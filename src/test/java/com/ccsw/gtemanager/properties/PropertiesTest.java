import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * TODO DOCS
 *
 */
@ExtendWith(MockitoExtension.class)
public class PropertiesTest {

    private static final String EXISTING_RUNDATE = "July 27, 2022 08:30 AM";

    @Mock
    private PropertiesRepository propertiesRepository;

    @InjectMocks
    private PropertiesServiceImpl propertiesService;

    /**
     * TODO DOCS
     * 
     */
    @Test
    public void getPropertyShouldReturnProperty() {
        assertEquals(EXISTING_RUNDATE, propertiesService.getProperty("LOAD_DATE"));
    }

    /**
     * TODO DOCS
     * 
     */
    @Test
    public void getInvalidPropertyShouldReturnError() {
        assertThrows(IllegalArgumentException.class, () -> propertiesService.getProperty("LOAD_DATES"));
    }


}
