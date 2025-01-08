package com.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dto.PaginationData;
import com.model.Movie;
import com.model.MovieSpecification;
import com.model.Rating;
import com.service.CategoryService;
import com.service.MovieService;
import com.service.RatingService;
import com.turkraft.springfilter.boot.Filter;
import com.utils.annotation.ApiMessage;
import com.utils.error.ResInvalidException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api")
public class MovieController {
    private final MovieService movieService;
    private final CategoryService cateService;
    private final RatingService ratingService;

    public MovieController(MovieService mvService, CategoryService ctService, RatingService rtService) {
        this.movieService = mvService;
        this.cateService = ctService;
        this.ratingService = rtService;
    }

    @PostMapping("/movies")
    @ApiMessage("Created movie")
    public ResponseEntity<Movie> createNewMovie(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("releaseDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate releaseDate,
            @RequestParam("category.categoryName") String categoryName,
            @RequestPart("movieImg") MultipartFile movieImg) {
        try {

            Movie movie = new Movie();
            movie.setTitle(title);
            movie.setDescription(description);
            movie.setReleaseDate(releaseDate);
            movie.setCategory(cateService.handleGetCategory(categoryName));
            movie.setMovieImg(movieImg.getBytes()); // Set the image as bytes

            Movie newMovie = movieService.handleCreateMovie(movie);
            return newMovie != null
                    ? ResponseEntity.status(HttpStatus.CREATED).body(newMovie)
                    : ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/movies/{id}")
    @ApiMessage("Deleted movie")
    public ResponseEntity<String> deleteMovie(@PathVariable("id") long id) throws ResInvalidException {
        if (this.movieService.handleGetMovie(id) == null) {
            throw new ResInvalidException("Movie doesn't exist");
        }
        this.movieService.handleDeleteMovie(id);
        return ResponseEntity.status(HttpStatus.OK).body("deleted");
    }

    @DeleteMapping("/movies/all")
    @ApiMessage("Data Refreshed")
    public ResponseEntity<String> deleteAllMovie() {
        String response = this.movieService.handleDeleteAll();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/movies/{id}")
    @ApiMessage("Get movie {id} success")
    @ResponseBody
    public ResponseEntity<Movie> getMovieByID(@PathVariable long id) throws ResInvalidException {
        Movie resMovie = this.movieService.handleGetMovie(id);
        if (resMovie == null) {
            throw new ResInvalidException("ID doesn't exist");
        }
        return ResponseEntity.status(HttpStatus.OK).body(resMovie);
    }

    @GetMapping("/movies")
    public ResponseEntity<PaginationData> getAllMovies(@Filter Specification<Movie> spec, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.movieService.handleGetAllMovie(spec, pageable));
    }

    @GetMapping("/movies/search")
    public ResponseEntity<PaginationData> handleSearch(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "minRating", required = false) Float minRating,
            @Filter Specification<Movie> spec,
            Pageable pageable) {
        Specification<Movie> combinedSpec = Specification.where(spec);
        if (title != null) {
            combinedSpec = combinedSpec.and(MovieSpecification.titleStartsWith(title));
        }
        if (minRating != null) {
            combinedSpec = combinedSpec.and(MovieSpecification.ratingGreaterThan(minRating));
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.movieService.handleGetAllMovie(combinedSpec, pageable));
    }

    @GetMapping("/movies/top")
    public ResponseEntity<List<Rating>> getMethodName(@PathVariable int minRating) {
        return ResponseEntity.status(HttpStatus.OK).body(this.ratingService.getRatingbyRatings(minRating));
    }

    @PutMapping("/movies/{id}")
    @ApiMessage("Movie has been updated")
    public ResponseEntity<Movie> updateMovie(@PathVariable long id, @RequestBody Movie mv) throws ResInvalidException {
        Movie targetMovie = this.movieService.handleUpdateMovie(id, mv);
        if (targetMovie == null) {
            throw new ResInvalidException("Movies doesn't exist");
        }
        return ResponseEntity.ok(targetMovie);
    }

    @GetMapping("/movies/newest")
    public ResponseEntity<List<Movie>> getNewestMovies(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.movieService.handleGetNewestMovie(pageable));
    }

}
