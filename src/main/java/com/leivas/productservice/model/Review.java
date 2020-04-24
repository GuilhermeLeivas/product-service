package com.leivas.productservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Document("review")
@Getter
@Setter
public class Review {

    @Id
    private String id;

    @NotEmpty
    private String userId;

    @NotEmpty
    private ReviewRate reviewRate;

    private String comment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", comment='" + comment + '\'' +
                ", reviewRate=" + reviewRate +
                '}';
    }
}
