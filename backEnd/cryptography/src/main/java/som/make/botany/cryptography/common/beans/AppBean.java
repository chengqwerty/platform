package som.make.botany.cryptography.common.beans;

import org.springframework.stereotype.Component;

@Component
public class AppBean {

    private String name = "earth";
    private String description = "This is a earth application!";
    private Integer year = 2018;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
