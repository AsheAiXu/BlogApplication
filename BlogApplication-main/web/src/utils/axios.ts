
import axios, { AxiosInstance } from "axios";
import useAuthStore from "../stores/auth";

export function api(): AxiosInstance {
    const instance = axios.create({
        baseURL: "/api",
        headers: {
            Authorization: `Bearer ${useAuthStore.getState()?.token}`,
            "Content-Type": "application/json",
            Accept: "application/json",
        },
    });

    instance.interceptors.response.use(
        (response) => {
            return response;
        },
        (error) => {
            if (error.response?.status === 401) {
                useAuthStore.getState().logout();
                location.href = "/login";
            }
            return Promise.reject(error);
        }
    );

    return instance;
}
