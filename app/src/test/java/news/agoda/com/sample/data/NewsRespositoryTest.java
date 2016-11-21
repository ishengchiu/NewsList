package news.agoda.com.sample.data;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Created by chiu_mac on 2016/11/20.
 */

public class NewsRespositoryTest {

    @Mock
    NewsServiceApi mNewsServiceApi;

    NewsRepository mNewsRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mNewsRepository = NewsRepositoryImpl.getInstance(mNewsServiceApi);
    }

    @Test
    public void getNews_requestAllNewsFromServiceApi() {
        mNewsRepository.getNews();

        verify(mNewsServiceApi).getAllNews();
    }
}
