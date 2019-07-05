package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.GameenginApplicationApp;
import io.github.jhipster.application.domain.Contest;
import io.github.jhipster.application.repository.ContestRepository;
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
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ContestResource} REST controller.
 */
@SpringBootTest(classes = GameenginApplicationApp.class)
public class ContestResourceIT {

    @Autowired
    private ContestRepository contestRepository;

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

    private MockMvc restContestMockMvc;

    private Contest contest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContestResource contestResource = new ContestResource(contestRepository);
        this.restContestMockMvc = MockMvcBuilders.standaloneSetup(contestResource)
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
    public static Contest createEntity(EntityManager em) {
        Contest contest = new Contest();
        return contest;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contest createUpdatedEntity(EntityManager em) {
        Contest contest = new Contest();
        return contest;
    }

    @BeforeEach
    public void initTest() {
        contest = createEntity(em);
    }

    @Test
    @Transactional
    public void createContest() throws Exception {
        int databaseSizeBeforeCreate = contestRepository.findAll().size();

        // Create the Contest
        restContestMockMvc.perform(post("/api/contests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contest)))
            .andExpect(status().isCreated());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeCreate + 1);
        Contest testContest = contestList.get(contestList.size() - 1);
    }

    @Test
    @Transactional
    public void createContestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contestRepository.findAll().size();

        // Create the Contest with an existing ID
        contest.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContestMockMvc.perform(post("/api/contests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contest)))
            .andExpect(status().isBadRequest());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContests() throws Exception {
        // Initialize the database
        contestRepository.saveAndFlush(contest);

        // Get all the contestList
        restContestMockMvc.perform(get("/api/contests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contest.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getContest() throws Exception {
        // Initialize the database
        contestRepository.saveAndFlush(contest);

        // Get the contest
        restContestMockMvc.perform(get("/api/contests/{id}", contest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contest.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingContest() throws Exception {
        // Get the contest
        restContestMockMvc.perform(get("/api/contests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContest() throws Exception {
        // Initialize the database
        contestRepository.saveAndFlush(contest);

        int databaseSizeBeforeUpdate = contestRepository.findAll().size();

        // Update the contest
        Contest updatedContest = contestRepository.findById(contest.getId()).get();
        // Disconnect from session so that the updates on updatedContest are not directly saved in db
        em.detach(updatedContest);

        restContestMockMvc.perform(put("/api/contests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContest)))
            .andExpect(status().isOk());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeUpdate);
        Contest testContest = contestList.get(contestList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingContest() throws Exception {
        int databaseSizeBeforeUpdate = contestRepository.findAll().size();

        // Create the Contest

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContestMockMvc.perform(put("/api/contests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contest)))
            .andExpect(status().isBadRequest());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContest() throws Exception {
        // Initialize the database
        contestRepository.saveAndFlush(contest);

        int databaseSizeBeforeDelete = contestRepository.findAll().size();

        // Delete the contest
        restContestMockMvc.perform(delete("/api/contests/{id}", contest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contest.class);
        Contest contest1 = new Contest();
        contest1.setId(1L);
        Contest contest2 = new Contest();
        contest2.setId(contest1.getId());
        assertThat(contest1).isEqualTo(contest2);
        contest2.setId(2L);
        assertThat(contest1).isNotEqualTo(contest2);
        contest1.setId(null);
        assertThat(contest1).isNotEqualTo(contest2);
    }
}
