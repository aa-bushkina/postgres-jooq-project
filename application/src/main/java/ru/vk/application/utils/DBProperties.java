package ru.vk.application.utils;

import org.jetbrains.annotations.NotNull;

public record DBProperties(@NotNull String connection,
                           @NotNull String name,
                           @NotNull String username,
                           @NotNull String password) {
}