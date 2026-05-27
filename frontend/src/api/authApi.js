import axios from "axios";

const AUTH_BASE_URL =
  "http://localhost:8082/auth";

export const registerUser =
  async (userData) => {

    return axios.post(
      `${AUTH_BASE_URL}/register`,
      userData
    );
  };

export const loginUser =
  async (userData) => {

    return axios.post(
      `${AUTH_BASE_URL}/login`,
      userData
    );
  };