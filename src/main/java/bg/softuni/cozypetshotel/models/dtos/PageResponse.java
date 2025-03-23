package bg.softuni.cozypetshotel.models.dtos;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.web.PagedModel;

import java.util.List;

public class PageResponse <T> {
    @NotNull
    private List<T> content;
    private PagedModel.PageMetadata page;

    public List<T> getContent() {
        return content;
    }

    public PageResponse<T> setContent(List<T> content) {
        this.content = content;
        return this;
    }

    public PagedModel.PageMetadata getPage() {
        return page;
    }

    public PageResponse<T> setPage(PagedModel.PageMetadata page) {
        this.page = page;
        return this;
    }
}
