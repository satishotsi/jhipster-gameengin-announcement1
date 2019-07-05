package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.GameenginApplicationApp;
import io.github.jhipster.application.domain.Announcement;
import io.github.jhipster.application.repository.AnnouncementRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.application.domain.enumeration.AnnouncementType;
/**
 * Integration tests for the {@Link AnnouncementResource} REST controller.
 */
@SpringBootTest(classes = GameenginApplicationApp.class)
public class AnnouncementResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final AnnouncementType DEFAULT_TYPE = AnnouncementType.CODE_HUNT;
    private static final AnnouncementType UPDATED_TYPE = AnnouncementType.QUIZ;

    private static final String DEFAULT_REWARD_INFO = "AAAAAAAAAA";
    private static final String UPDATED_REWARD_INFO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CONTEST_ID = 1L;
    private static final Long UPDATED_CONTEST_ID = 2L;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAnnouncementMockMvc;

    private Announcement announcement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnnouncementResource announcementResource = new AnnouncementResource(announcementRepository);
        this.restAnnouncementMockMvc = MockMvcBuilders.standaloneSetup(announcementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Announcement createEntity(EntityManager em) {
        Announcement announcement = new Announcement()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .active(DEFAULT_ACTIVE)
            .type(DEFAULT_TYPE)
            .rewardInfo(DEFAULT_REWARD_INFO)
            .createdDate(DEFAULT_CREATED_DATE)
            .contestId(DEFAULT_CONTEST_ID);
        return announcement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Announcement createUpdatedEntity(EntityManager em) {
        Announcement announcement = new Announcement()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE)
            .type(UPDATED_TYPE)
            .rewardInfo(UPDATED_REWARD_INFO)
            .createdDate(UPDATED_CREATED_DATE)
            .contestId(UPDATED_CONTEST_ID);
        return announcement;
    }

    @BeforeEach
    public void initTest() {
        announcement = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnnouncement() throws Exception {
        int databaseSizeBeforeCreate = announcementRepository.findAll().size();

        // Create the Announcement
        restAnnouncementMockMvc.perform(post("/api/announcements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(announcement)))
            .andExpect(status().isCreated());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeCreate + 1);
        Announcement testAnnouncement = announcementList.get(announcementList.size() - 1);
        assertThat(testAnnouncement.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAnnouncement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAnnouncement.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testAnnouncement.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAnnouncement.getRewardInfo()).isEqualTo(DEFAULT_REWARD_INFO);
        assertThat(testAnnouncement.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAnnouncement.getContestId()).isEqualTo(DEFAULT_CONTEST_ID);
    }

    @Test
    @Transactional
    public void createAnnouncementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = announcementRepository.findAll().size();

        // Create the Announcement with an existing ID
        announcement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnnouncementMockMvc.perform(post("/api/announcements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(announcement)))
            .andExpect(status().isBadRequest());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnnouncements() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get all the announcementList
        restAnnouncementMockMvc.perform(get("/api/announcements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(announcement.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].rewardInfo").value(hasItem(DEFAULT_REWARD_INFO.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].contestId").value(hasItem(DEFAULT_CONTEST_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getAnnouncement() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        // Get the announcement
        restAnnouncementMockMvc.perform(get("/api/announcements/{id}", announcement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(announcement.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.rewardInfo").value(DEFAULT_REWARD_INFO.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.contestId").value(DEFAULT_CONTEST_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAnnouncement() throws Exception {
        // Get the announcement
        restAnnouncementMockMvc.perform(get("/api/announcements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnnouncement() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();

        // Update the announcement
        Announcement updatedAnnouncement = announcementRepository.findById(announcement.getId()).get();
        // Disconnect from session so that the updates on updatedAnnouncement are not directly saved in db
        em.detach(updatedAnnouncement);
        updatedAnnouncement
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE)
            .type(UPDATED_TYPE)
            .rewardInfo(UPDATED_REWARD_INFO)
            .createdDate(UPDATED_CREATED_DATE)
            .contestId(UPDATED_CONTEST_ID);

        restAnnouncementMockMvc.perform(put("/api/announcements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnnouncement)))
            .andExpect(status().isOk());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
        Announcement testAnnouncement = announcementList.get(announcementList.size() - 1);
        assertThat(testAnnouncement.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAnnouncement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAnnouncement.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAnnouncement.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAnnouncement.getRewardInfo()).isEqualTo(UPDATED_REWARD_INFO);
        assertThat(testAnnouncement.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAnnouncement.getContestId()).isEqualTo(UPDATED_CONTEST_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingAnnouncement() throws Exception {
        int databaseSizeBeforeUpdate = announcementRepository.findAll().size();

        // Create the Announcement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnnouncementMockMvc.perform(put("/api/announcements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(announcement)))
            .andExpect(status().isBadRequest());

        // Validate the Announcement in the database
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnnouncement() throws Exception {
        // Initialize the database
        announcementRepository.saveAndFlush(announcement);

        int databaseSizeBeforeDelete = announcementRepository.findAll().size();

        // Delete the announcement
        restAnnouncementMockMvc.perform(delete("/api/announcements/{id}", announcement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Announcement> announcementList = announcementRepository.findAll();
        assertThat(announcementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Announcement.class);
        Announcement announcement1 = new Announcement();
        announcement1.setId(1L);
        Announcement announcement2 = new Announcement();
        announcement2.setId(announcement1.getId());
        assertThat(announcement1).isEqualTo(announcement2);
        announcement2.setId(2L);
        assertThat(announcement1).isNotEqualTo(announcement2);
        announcement1.setId(null);
        assertThat(announcement1).isNotEqualTo(announcement2);
    }
}
