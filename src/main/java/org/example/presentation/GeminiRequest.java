package org.example.presentation;

import lombok.Getter;

import java.util.List;

@Getter
public class GeminiRequest {

    private final List<Content> contents;

    public GeminiRequest(String prompt) {
        this.contents = List.of(
                new Content(
                        List.of(new Part(prompt))
                )
        );
    }

    @Getter
    private static class Content {
        private final List<Part> parts;

        private Content(List<Part> parts) {
            this.parts = parts;
        }
    }

    @Getter
    private static class Part {
        private final String text;

        private Part(String text) {
            this.text = text;
        }
    }
}
