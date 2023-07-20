package ru.practicum.event.service;

import com.querydsl.core.BooleanBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.Comment.dto.CommentDto;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.dto.StatsDtoResponse;
import ru.practicum.error.NotFoundException;
import ru.practicum.event.dto.*;
import ru.practicum.event.enums.AdminActionState;
import ru.practicum.event.enums.EventSort;
import ru.practicum.event.enums.EventState;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.QEvent;
import ru.practicum.event.model.SearchFilter;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.locations.mapper.LocationMapper;
import ru.practicum.locations.model.Location;
import ru.practicum.locations.repository.LocationRepository;
import ru.practicum.model.StatsClient;
import ru.practicum.rating.Service.RatingService;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.util.PageCalculate;
import ru.practicum.util.ValidateManager;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.practicum.event.enums.EventState.PUBLISHED;
import static ru.practicum.request.enums.RequestStatus.CONFIRMED;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventServiceImpl implements EventService {

    EventRepository eventRepository;

    UserRepository userRepository;

    CategoryRepository categoryRepository;

    LocationRepository locationRepository;

    RequestRepository requestRepository;

    RatingService ratingService;

    StatsClient statsClient;

    @Override
    public EventFullDto create(Long userId, NewEventDto newEventDto) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found."));
        ValidateManager.checkId(userRepository, userId);
        Category category = categoryRepository.getReferenceById(newEventDto.getCategory());

        ValidateManager.checkId(categoryRepository, newEventDto.getCategory());
        Location location = LocationMapper.toLocation(newEventDto.getLocation());
        location = locationRepository.existsByLatAndLon(location.getLat(), location.getLon())
                ? locationRepository.findByLatAndLon(location.getLat(), location.getLon()) : locationRepository.save(location);

        Event event = EventMapper.newEventDtoRequestToEvent(newEventDto);
        event.setInitiator(user);
        event.setCategory(category);
        event.setLocation(location);
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto findById(Long userId, Long eventId) {
        return EventMapper.toEventFullDto(eventRepository.findByIdAndInitiatorId(eventId, userId));
    }

    @Override
    public List<EventShortDto> findById(Long userId, Integer from, Integer size) {
        return eventRepository.findAllByInitiatorId(userId, PageCalculate.getPage(from, size))
                .map(EventMapper::toEventShortDto).getContent();
    }

    @Override
    public EventFullDto updateByIdInitiator(Long initiatorId, Long eventId, UpdateEventUserRequest dto) {

        Event event = eventRepository.findByIdAndInitiatorId(eventId, initiatorId);
        if (Objects.isNull(event)) {
            throw new NotFoundException(String.format("Not found %s", dto.getClass().getSimpleName()));
        }
        if (event.getState().equals(PUBLISHED)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Can't change, because it already Published.");
        }

        if (Objects.nonNull(dto.getCategory())) {
            ValidateManager.checkId(categoryRepository, dto.getCategory());
        }

        if (dto.getLocation() != null) {
            Location location = LocationMapper.toLocation(dto.getLocation());
            location = locationRepository.existsByLatAndLon(location.getLat(), location.getLon())
                    ? locationRepository.findByLatAndLon(location.getLat(), location.getLon())
                    : locationRepository.save(location);
            event.setLocation(location);
        }

        return EventMapper.toEventFullDto(eventRepository.save(EventMapper.updateEventFromUser(dto, event)));
    }

    @Override
    public EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest dto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found."));

        if (dto.getStateAction() != null) {

            if (!event.getState().equals(EventState.PENDING) &&
                    dto.getStateAction().equals(AdminActionState.PUBLISH_EVENT)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Event is not Pending");
            }
            if (event.getState().equals(PUBLISHED) &&
                    dto.getStateAction().equals(AdminActionState.REJECT_EVENT)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Event is not Published yet");
            }
        }

        if (dto.getLocation() != null) {
            Location location = LocationMapper.toLocation(dto.getLocation());
            location = locationRepository.existsByLatAndLon(location.getLat(), location.getLon())
                    ? locationRepository.findByLatAndLon(location.getLat(), location.getLon())
                    : locationRepository.save(location);
            event.setLocation(location);
        }

        return EventMapper.toEventFullDto(eventRepository.save(EventMapper.updateEventFromAdmin(dto, event)));
    }

    @Override
    public List<EventShortDto> searchUsingFilterAndSorting(SearchFilter filter, EventSort eventSort, Integer from, Integer size) {
        String sort = eventSort.getEventSort(eventSort);
        BooleanBuilder booleanBuilder = makeBuilder(filter);
        Page<Event> page = eventRepository
                .findAll(booleanBuilder, PageCalculate.getPage(from, size, sort == null ?
                        Sort.unsorted() : Sort.by(Sort.Direction.DESC, sort)));

        if (eventSort.equals(EventSort.RATE)) {
            return page.getContent().stream().map(EventMapper::toEventShortDto)
                    .sorted(Comparator.comparing(EventShortDto::getRate, Comparator.reverseOrder()))
                    .collect(Collectors.toList());
        }

        return page.getContent().stream().map(EventMapper::toEventShortDto).collect(Collectors.toList());
    }


    @Override
    public List<EventFullDto> searchEventsFromAdmin(SearchFilter filter, Integer from, Integer size) {
        BooleanBuilder booleanBuilder = makeBuilder(filter);
        Page<Event> page = eventRepository.findAll(booleanBuilder, PageCalculate.getPage(from, size));
        setViews(page.getContent());

        if(filter.getSort() != null){
            return page.getContent().stream().map(EventMapper::toEventFullDto)
                    .sorted(Comparator.comparing(EventFullDto::getRate, Comparator.reverseOrder()))
                    .collect(Collectors.toList());
        } else {
            return  page.getContent().stream().map(EventMapper::toEventFullDto)
                    .collect(Collectors.toList());
        }




    }

    @Override
    public EventFullDto publicFindById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found."));
        if (event.getState() != PUBLISHED) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found.");
        }

        setViews(List.of(event));
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public Object manageEstimate(Long userId, Long eventId, Integer rate, CommentDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        ValidateManager.checkId(eventRepository, eventId);
        Event event = eventRepository.findEventByIdAndState(eventId, PUBLISHED);

        if (rate == null) {
            ratingService.manageEstimate(user,
                    event, 0, dto);
        }

        if (!userId.equals(event.getInitiator().getId()) &&
                requestRepository.existsByRequesterIdAndEventIdAndStatus(userId, eventId, CONFIRMED)) {
            return ratingService.manageEstimate(user, event, rate, dto);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The initiator cannot vote");
        }
    }

    private String setView(Event event) {
        return String.format("/events/%s", event.getId());
    }


    private BooleanBuilder makeBuilder(SearchFilter filter) {
        java.util.function.Predicate<Object> isNullOrEmpty = obj ->
                Objects.isNull(obj) || (obj instanceof Collection && ((Collection<?>) obj).isEmpty());
        QEvent qEvent = QEvent.event;
        return new BooleanBuilder()
                .and(!isNullOrEmpty.test(filter.getUserIds()) ? qEvent.initiator.id.in(filter.getUserIds()) : null)
                .and(!isNullOrEmpty.test(filter.getStates()) ? qEvent.state.in(filter.getStates()) : null)
                .and(!isNullOrEmpty.test(filter.getCategoryIds()) ? qEvent.category.id.in(filter.getCategoryIds()) : null)
                .and(!isNullOrEmpty.test(filter.getRangeStart()) ? qEvent.eventDate.after(filter.getRangeStart()) : null)
                .and(!isNullOrEmpty.test(filter.getRangeEnd()) ? qEvent.eventDate.before(filter.getRangeEnd()) : null)
                .and(!isNullOrEmpty.test(filter.getPaid()) ? qEvent.paid.eq(filter.getPaid()) : null)
                .and(!isNullOrEmpty.test(filter.getText()) ? (qEvent.annotation.likeIgnoreCase(filter.getText())
                        .or(qEvent.description.likeIgnoreCase(filter.getText()))) : null)
                .and(!isNullOrEmpty.test(filter.getOnlyAvailable()) ? qEvent.participantLimit.eq(0)
                        .or(qEvent.confirmedRequests.lt(qEvent.participantLimit)) : null);
    }

    private void setViews(List<Event> events) {
        setViews(events, LocalDateTime.now().minusYears(100), LocalDateTime.now());
    }

    private void setViews(List<Event> events, LocalDateTime start, LocalDateTime end) {
        List<String> uris = events.stream()
                .map(this::setView)
                .collect(Collectors.toList());

        List<StatsDtoResponse> statsList = statsClient.stats(start, end,
                uris, true);

        for (Event event : events) {
            StatsDtoResponse currentStat = statsList.stream()
                    .filter(statsDto -> {
                        Long eventIdOfViewStats = Long.parseLong(statsDto.getUri().substring("/events/".length()));
                        return eventIdOfViewStats.equals(event.getId());
                    })
                    .findFirst()
                    .orElse(null);

            event.setViews(currentStat != null ? currentStat.getHits() : 0L);
        }
        eventRepository.saveAll(events);
    }

}
