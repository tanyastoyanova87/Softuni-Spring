package app.story.service;

import app.story.model.Story;
import app.story.repository.SharedStoriesRepository;
import app.story.repository.StoryRepository;
import app.user.model.User;
import app.web.dto.AddStoryRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class StoryService {

    private final StoryRepository storyRepository;
    private final SharedStoriesRepository sharedStoriesRepository;

    public StoryService(StoryRepository storyRepository, SharedStoriesRepository sharedStoriesRepository) {
        this.storyRepository = storyRepository;
        this.sharedStoriesRepository = sharedStoriesRepository;
    }

    public void addNewStory(User user, AddStoryRequest addStoryRequest) {
        Story story = Story.builder()
                .title(addStoryRequest.getTitle())
                .description(addStoryRequest.getDescription())
                .kind(addStoryRequest.getKind())
                .date(addStoryRequest.getDate())
                .addedOn(LocalDate.now())
                .owner(user)
                .isVisible(false)
                .build();

        storyRepository.save(story);
    }

    public void deleteStory(UUID id) {
        storyRepository.deleteById(id);
    }

    public Story getById(UUID id) {
        return storyRepository.findById(id).orElseThrow(() -> new RuntimeException("Story with id [%s] does not exist.".formatted(id)));
    }

    public void makeStoryVisible(Story story) {
        story.setVisible(true);
    }

    public void addStoryToSharedStories(Story visibleStory) {
        sharedStoriesRepository.save(visibleStory);
    }

    public List<Story> allSharedStories() {
        return sharedStoriesRepository.findAll();
    }
}
