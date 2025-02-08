package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.HallDto;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.HallRepository;

import java.util.Optional;

@ThreadSafe
@Service
public class SimpleHallService implements HallService {

    private final HallRepository repository;

    public SimpleHallService(HallRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Hall> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<HallDto> findByName(String name) {
        var optionalHall = repository.findByName(name);
        HallDto hallDto = null;
        if (optionalHall.isPresent()) {
            var hall = optionalHall.get();
            hallDto = new HallDto(hall.getId(), hall.getName(), getCount(hall.getRowCount()),
                    getCount(hall.getPlaceCount()), hall.getDescription());
        }
        return Optional.ofNullable(hallDto);
    }

    private int[] getCount(int i) {
        int[] rsl = new int[i + 1];
        for (int r = 0; r <= i; r++) {
            rsl[r] = r;
        }
        return rsl;
    }
}
