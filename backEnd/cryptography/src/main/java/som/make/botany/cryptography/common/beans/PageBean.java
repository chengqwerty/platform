package som.make.botany.cryptography.common.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Transient;

public class PageBean {

    private Integer page;
    private Integer size;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @JsonIgnore
    public Integer getQueryPage() {
        return this.page - 1;
    }

}
