package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Contest;
import io.github.jhipster.application.repository.ContestRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.jhipster.application.domain.Contest}.
 */
@RestController
@RequestMapping("/api")
public class ContestResource {

    private final Logger log = LoggerFactory.getLogger(ContestResource.class);

    private static final String ENTITY_NAME = "contest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContestRepository contestRepository;

    public ContestResource(ContestRepository contestRepository) {
        this.contestRepository = contestRepository;
    }

    /**
     * {@code POST  /contests} : Create a new contest.
     *
     * @param contest the contest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contest, or with status {@code 400 (Bad Request)} if the contest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contests")
    public ResponseEntity<Contest> createContest(@RequestBody Contest contest) throws URISyntaxException {
        log.debug("REST request to save Contest : {}", contest);
        if (contest.getId() != null) {
            throw new BadRequestAlertException("A new contest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Contest result = contestRepository.save(contest);
        return ResponseEntity.created(new URI("/api/contests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contests} : Updates an existing contest.
     *
     * @param contest the contest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contest,
     * or with status {@code 400 (Bad Request)} if the contest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contests")
    public ResponseEntity<Contest> updateContest(@RequestBody Contest contest) throws URISyntaxException {
        log.debug("REST request to update Contest : {}", contest);
        if (contest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Contest result = contestRepository.save(contest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contest.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contests} : get all the contests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contests in body.
     */
    @GetMapping("/contests")
    public List<Contest> getAllContests() {
        log.debug("REST request to get all Contests");
        return contestRepository.findAll();
    }

    /**
     * {@code GET  /contests/:id} : get the "id" contest.
     *
     * @param id the id of the contest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contests/{id}")
    public ResponseEntity<Contest> getContest(@PathVariable Long id) {
        log.debug("REST request to get Contest : {}", id);
        Optional<Contest> contest = contestRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contest);
    }

    /**
     * {@code DELETE  /contests/:id} : delete the "id" contest.
     *
     * @param id the id of the contest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contests/{id}")
    public ResponseEntity<Void> deleteContest(@PathVariable Long id) {
        log.debug("REST request to delete Contest : {}", id);
        contestRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
