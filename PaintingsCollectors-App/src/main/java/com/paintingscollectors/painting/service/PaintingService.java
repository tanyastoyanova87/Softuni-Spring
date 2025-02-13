package com.paintingscollectors.painting.service;

import com.paintingscollectors.painting.model.FavouritePainting;
import com.paintingscollectors.painting.model.Painting;
import com.paintingscollectors.painting.repository.FavouritePaintingRepository;
import com.paintingscollectors.painting.repository.PaintingRepository;
import com.paintingscollectors.user.model.User;
import com.paintingscollectors.web.dto.CreateNewPainting;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaintingService {

    private final PaintingRepository paintingRepository;
    private final FavouritePaintingRepository favouritePaintingRepository;

    public PaintingService(PaintingRepository paintingRepository, FavouritePaintingRepository favouritePaintingRepository) {
        this.paintingRepository = paintingRepository;
        this.favouritePaintingRepository = favouritePaintingRepository;
    }

    public void createPainting(CreateNewPainting createNewPainting, User user) {
        Painting painting = Painting.builder()
                .name(createNewPainting.getName())
                .author(createNewPainting.getAuthor())
                .style(createNewPainting.getStyle())
                .imageUrl(createNewPainting.getImageUrl())
                .votes(0)
                .owner(user)
                .build();

        this.paintingRepository.save(painting);
    }

    public void deletePaintingById(UUID id) {
        this.paintingRepository.deleteById(id);
    }

    public List<Painting> getAllPaintings() {
        return this.paintingRepository.findAllByOrderByVotesDescNameAsc();
    }

    public void makeFavouritePainting(User user, UUID id) {
        Painting painting = getById(id);

        FavouritePainting favouritePainting = FavouritePainting.builder()
                .name(painting.getName())
                .author(painting.getAuthor())
                .imageUrl(painting.getImageUrl())
                .owner(user)
                .createdOn(LocalDateTime.now())
                .build();

        this.favouritePaintingRepository.save(favouritePainting);
    }

    public Painting getById(UUID id) {
        return this.paintingRepository.findById(id).orElseThrow(() -> new RuntimeException("Painting with id [%s] does not exist.".formatted(id)));
    }

    public void incrementVotesByOne(UUID id) {
        Painting painting = getById(id);
        painting.setVotes(painting.getVotes() + 1);
        this.paintingRepository.save(painting);
    }

    public void deleteFavouritePaintingById(UUID id) {
        this.favouritePaintingRepository.deleteById(id);
    }
}