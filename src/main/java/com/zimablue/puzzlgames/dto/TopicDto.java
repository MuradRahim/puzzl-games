package com.zimablue.puzzlgames.dto;

import com.zimablue.puzzlgames.model.Question;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {
    private Long id;
    private String slug;
    private String title;
    private String description;
    private Long questions;
}
