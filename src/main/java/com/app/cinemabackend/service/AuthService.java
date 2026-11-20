package com.app.cinemabackend.service;

import com.app.cinemabackend.dto.request.LoginRequestDTO;
import com.app.cinemabackend.dto.request.RegisterRequestDTO;
import com.app.cinemabackend.dto.response.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO login(LoginRequestDTO request);
    AuthResponseDTO register(RegisterRequestDTO request);
}