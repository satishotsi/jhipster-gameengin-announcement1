package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Announcement;
import io.github.jhipster.application.repository.AnnouncementRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.jhipster.application.domain.Announcement}.
 */
@RestController
@RequestMapping("/api")
public class AnnouncementResource {

    private final Logger log = LoggerFactory.getLogger(AnnouncementResource.class);

    private static final String ENTITY_NAME = "announcement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnnouncementRepository announcementRepository;

    public AnnouncementResource(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    /**
     * {@code POST  /announcements} : Create a new announcement.
     *
     * @param announcement the announcement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new announcement, or with status {@code 400 (Bad Request)} if the announcement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/announcements")
    public ResponseEntity<Announcement> createAnnouncement(@RequestBody Announcement announcement) throws URISyntaxException {
        log.debug("REST request to save Announcement : {}", announcement);
        if (announcement.getId() != null) {
            throw new BadRequestAlertException("A new announcement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Announcement result = announcementRepository.save(announcement);
        return ResponseEntity.created(new URI("/api/announcements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /announcements} : Updates an existing announcement.
     *
     * @param announcement the announcement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated announcement,
     * or with status {@code 400 (Bad Request)} if the announcement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the announcement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/announcements")
    public ResponseEntity<Announcement> updateAnnouncement(@RequestBody Announcement announcement) throws URISyntaxException {
        log.debug("REST request to update Announcement : {}", announcement);
        if (announcement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Announcement result = announcementRepository.save(announcement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, announcement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /announcements} : get all the announcements.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of announcements in body.
     */
    @GetMapping("/announcements")
    public ResponseEntity<List<Announcement>> getAllAnnouncements(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Announcements");
        Page<Announcement> page = announcementRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /announcements/:id} : get the "id" announcement.
     *
     * @param id the id of the announcement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the announcement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/announcements/{id}")
    public ResponseEntity<Announcement> getAnnouncement(@PathVariable Long id) {
        log.debug("REST request to get Announcement : {}", id);
        Optional<Announcement> announcement = announcementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(announcement);
    }

    /**
     * {@code DELETE  /announcements/:id} : delete the "id" announcement.
     *
     * @param id the id of the announcement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/announcements/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        log.debug("REST request to delete Announcement : {}", id);
        announcementRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
