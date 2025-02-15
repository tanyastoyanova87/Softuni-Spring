package Philately.stamp.service;

import Philately.stamp.model.Stamp;
import Philately.stamp.model.WishedStamp;
import Philately.stamp.repository.StampRepository;
import Philately.stamp.repository.WishedStampRepository;
import Philately.user.model.User;
import Philately.web.dto.AddStampRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StampService {

    private final StampRepository stampRepository;
    private final WishedStampRepository wishedStampRepository;

    public StampService(StampRepository stampRepository, WishedStampRepository wishedStampRepository) {
        this.stampRepository = stampRepository;
        this.wishedStampRepository = wishedStampRepository;
    }


    public void addStamp(AddStampRequest addStampRequest, User user) {
        Stamp stamp = Stamp.builder()
                .name(addStampRequest.getName())
                .description(addStampRequest.getDescription())
                .imageUrl(addStampRequest.getImageUrl())
                .paper(addStampRequest.getPaper())
                .owner(user)
                .build();

        this.stampRepository.save(stamp);
    }

    public List<Stamp> getAll() {
        return this.stampRepository.findAll();
    }

    public Stamp getById(UUID id) {
        return this.stampRepository.findById(id).orElseThrow(() -> new RuntimeException("Stamp with id %s does not exist.".formatted(id)));
    }

    public void addStampToWishList(UUID id, User user) {
        Stamp stamp = getById(id);

        WishedStamp wishedStamp = WishedStamp.builder()
                .name(stamp.getName())
                .description(stamp.getDescription())
                .owner(user)
                .imageUrl(stamp.getImageUrl())
                .build();

        this.wishedStampRepository.save(wishedStamp);
    }

    public void deleteStampFromWishlist(UUID id) {
        this.wishedStampRepository.deleteById(id);
    }
}
