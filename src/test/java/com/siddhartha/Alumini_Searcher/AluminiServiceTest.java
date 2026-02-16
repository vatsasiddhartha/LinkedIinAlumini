package com.siddhartha.Alumini_Searcher;

import com.siddhartha.Alumini_Searcher.Entity.Alumini;
import com.siddhartha.Alumini_Searcher.Repository.AluminiRepository;
import com.siddhartha.Alumini_Searcher.Services.AluminiService;
import com.siddhartha.Alumini_Searcher.Services.PhantomBusterService;
import com.siddhartha.Alumini_Searcher.dto.RequestDto;
import com.siddhartha.Alumini_Searcher.dto.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Alumni Service Test Suite")
class AluminiServiceTest {

    @Mock
    private AluminiRepository aluminiRepository;

    @Mock
    private PhantomBusterService phantomBusterService;

    @InjectMocks
    private AluminiService aluminiService;

    private RequestDto requestDto;

    @BeforeEach
    void setUp() {
        requestDto = new RequestDto();
        requestDto.setUniversity("IIT Delhi");
        requestDto.setDesignation("Software Engineer");
        requestDto.setPassoutYear(2023);
    }

    /**
     * TEST 1: Successful fetch, parse, and save alumni from Phantom API
     */
    @Test
    @DisplayName("Should successfully fetch, parse and save alumni")
    void shouldFetchParseAndSaveAlumni() {
        // Arrange
        String phantomJsonResponse = """
        {
          "container": {
            "results": [
              {
                "fullName": "Rahul Sharma",
                "jobTitle": "Software Engineer",
                "schoolName": "IIT Delhi",
                "location": "Delhi, India"
              }
            ]
          }
        }
        """;

        when(phantomBusterService.fetchAlumniFromPhantom())
                .thenReturn(phantomJsonResponse);

        when(aluminiRepository.save(any(Alumini.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        List<Response> result = aluminiService.searchAlumini(requestDto);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Rahul Sharma", result.get(0).getName());
        assertEquals("Software Engineer", result.get(0).getDesignation());
        assertEquals("IIT Delhi", result.get(0).getUniversity());

        verify(phantomBusterService, times(1)).fetchAlumniFromPhantom();
        verify(aluminiRepository, times(1)).save(any(Alumini.class));
    }

    /**
     * TEST 2: Filter returns empty when no match found
     */
    @Test
    @DisplayName("Should return empty list when no alumni match criteria")
    void shouldReturnEmptyWhenNoMatch() {
        // Arrange
        String phantomJsonResponse = """
        {
          "container": {
            "results": [
              {
                "fullName": "Amit Kumar",
                "jobTitle": "Designer",
                "schoolName": "ABC College",
                "location": "Mumbai, India"
              }
            ]
          }
        }
        """;

        when(phantomBusterService.fetchAlumniFromPhantom())
                .thenReturn(phantomJsonResponse);

        // Act
        List<Response> result = aluminiService.searchAlumini(requestDto);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(aluminiRepository, never()).save(any(Alumini.class));
    }

    /**
     * TEST 3: Handle multiple alumni matching criteria
     */
    @Test
    @DisplayName("Should return multiple alumni when multiple matches found")
    void shouldReturnMultipleAlumni() {
        // Arrange
        String phantomJsonResponse = """
        {
          "container": {
            "results": [
              {
                "fullName": "Rahul Sharma",
                "jobTitle": "Software Engineer",
                "schoolName": "IIT Delhi",
                "location": "Delhi"
              },
              {
                "fullName": "Priya Patel",
                "jobTitle": "Software Engineer",
                "schoolName": "IIT Delhi",
                "location": "Bangalore"
              },
              {
                "fullName": "Wrong Person",
                "jobTitle": "Manager",
                "schoolName": "IIT Delhi",
                "location": "Mumbai"
              }
            ]
          }
        }
        """;

        when(phantomBusterService.fetchAlumniFromPhantom())
                .thenReturn(phantomJsonResponse);

        when(aluminiRepository.save(any(Alumini.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        List<Response> result = aluminiService.searchAlumini(requestDto);

        // Assert
        assertEquals(2, result.size(), "Should return only matching alumni");
        assertTrue(result.stream().allMatch(r -> r.getDesignation().equals("Software Engineer")));
        verify(aluminiRepository, times(2)).save(any(Alumini.class));
    }

    /**
     * TEST 4: Handle empty results array from Phantom API
     */
    @Test
    @DisplayName("Should handle empty results from Phantom API")
    void shouldHandleEmptyPhantomResults() {
        // Arrange
        String emptyJsonResponse = """
        {
          "container": {
            "results": []
          }
        }
        """;

        when(phantomBusterService.fetchAlumniFromPhantom())
                .thenReturn(emptyJsonResponse);

        // Act
        List<Response> result = aluminiService.searchAlumini(requestDto);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(aluminiRepository, never()).save(any(Alumini.class));
    }

    /**
     * TEST 5: Filter by university only (no designation filter)
     */
    @Test
    @DisplayName("Should filter by university when designation is null")
    void shouldFilterByUniversityOnly() {
        // Arrange
        String phantomJsonResponse = """
        {
          "container": {
            "results": [
              {
                "fullName": "Student A",
                "jobTitle": "Engineer",
                "schoolName": "IIT Delhi",
                "location": "Delhi"
              },
              {
                "fullName": "Student B",
                "jobTitle": "Designer",
                "schoolName": "IIT Delhi",
                "location": "Delhi"
              }
            ]
          }
        }
        """;

        when(phantomBusterService.fetchAlumniFromPhantom())
                .thenReturn(phantomJsonResponse);

        when(aluminiRepository.save(any(Alumini.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act - Only set university filter
        requestDto.setDesignation(null);
        List<Response> result = aluminiService.searchAlumini(requestDto);

        // Assert
        assertEquals(2, result.size(), "Should return all alumni from IIT Delhi");
        verify(aluminiRepository, times(2)).save(any(Alumini.class));
    }

    /**
     * TEST 6: Partial university name matching
     */
    @Test
    @DisplayName("Should match university names partially")
    void shouldMatchUniversityPartially() {
        // Arrange
        String phantomJsonResponse = """
        {
          "container": {
            "results": [
              {
                "fullName": "Student A",
                "jobTitle": "Software Engineer",
                "schoolName": "IIT Delhi",
                "location": "Delhi"
              },
              {
                "fullName": "Student B",
                "jobTitle": "Software Engineer",
                "schoolName": "IIT Bombay",
                "location": "Mumbai"
              },
              {
                "fullName": "Student C",
                "jobTitle": "Software Engineer",
                "schoolName": "NIT Trichy",
                "location": "Trichy"
              }
            ]
          }
        }
        """;

        when(phantomBusterService.fetchAlumniFromPhantom())
                .thenReturn(phantomJsonResponse);

        when(aluminiRepository.save(any(Alumini.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act - Search for "IIT" universities
        requestDto.setUniversity("IIT");
        List<Response> result = aluminiService.searchAlumini(requestDto);

        // Assert
        assertEquals(2, result.size(), "Should return both IIT alumni");
        assertTrue(result.stream().allMatch(r -> r.getUniversity().contains("IIT")));
    }

    /**
     * TEST 7: Case-insensitive filtering
     */
    @Test
    @DisplayName("Should perform case-insensitive matching")
    void shouldMatchCaseInsensitively() {
        // Arrange
        String phantomJsonResponse = """
        {
          "container": {
            "results": [
              {
                "fullName": "Test User",
                "jobTitle": "software engineer",
                "schoolName": "iit delhi",
                "location": "Delhi"
              }
            ]
          }
        }
        """;

        when(phantomBusterService.fetchAlumniFromPhantom())
                .thenReturn(phantomJsonResponse);

        when(aluminiRepository.save(any(Alumini.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act - Use different case in search criteria
        requestDto.setUniversity("IIT DELHI");
        requestDto.setDesignation("SOFTWARE ENGINEER");
        List<Response> result = aluminiService.searchAlumini(requestDto);

        // Assert
        assertEquals(1, result.size(), "Should match case-insensitively");
    }

    /**
     * TEST 8: Handle null or empty filters
     */
    @Test
    @DisplayName("Should return all results when filters are null")
    void shouldReturnAllWhenFiltersAreNull() {
        // Arrange
        String phantomJsonResponse = """
        {
          "container": {
            "results": [
              {
                "fullName": "Person 1",
                "jobTitle": "Engineer",
                "schoolName": "University A",
                "location": "City A"
              },
              {
                "fullName": "Person 2",
                "jobTitle": "Designer",
                "schoolName": "University B",
                "location": "City B"
              }
            ]
          }
        }
        """;

        when(phantomBusterService.fetchAlumniFromPhantom())
                .thenReturn(phantomJsonResponse);

        when(aluminiRepository.save(any(Alumini.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act - No filters
        requestDto.setUniversity(null);
        requestDto.setDesignation(null);
        List<Response> result = aluminiService.searchAlumini(requestDto);

        // Assert
        assertEquals(2, result.size(), "Should return all alumni when no filters applied");
    }

    /**
     * TEST 9: Verify data is saved to repository
     */
    @Test
    @DisplayName("Should save alumni to repository")
    void shouldSaveAlumniToRepository() {
        // Arrange
        String phantomJsonResponse = """
        {
          "container": {
            "results": [
              {
                "fullName": "John Doe",
                "jobTitle": "Software Engineer",
                "schoolName": "IIT Delhi",
                "location": "Delhi"
              }
            ]
          }
        }
        """;

        when(phantomBusterService.fetchAlumniFromPhantom())
                .thenReturn(phantomJsonResponse);

        when(aluminiRepository.save(any(Alumini.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        aluminiService.searchAlumini(requestDto);

        // Assert - Verify save was called with correct data
        verify(aluminiRepository).save(argThat(alumni ->
                "John Doe".equals(alumni.getName()) &&
                        "Software Engineer".equals(alumni.getJobRole()) &&
                        "IIT Delhi".equals(alumni.getUniversity())
        ));
    }
}