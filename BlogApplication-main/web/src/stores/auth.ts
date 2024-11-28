
import { create } from "zustand";
import { createJSONStorage, persist } from "zustand/middleware";
import { User } from "../models/user.ts";

interface AuthState {
    user?: User;
    token?: string;
    setToken: (token: string) => void;
    setUser: (user: User) => void;
    logout: () => void;
}

const useAuthStore = create<AuthState>()(
    persist(
        (set) => ({
            setToken: (token: string) => {
                set({ token: token });
            },
            setUser: (user: User) => {
                set({ user: user });
            },
            logout: () => {
                set({
                    user: undefined,
                    token: undefined,
                });
            },
        }),
        {
            name: "auth_store",
            storage: createJSONStorage(() => localStorage),
        }
    )
);

export default useAuthStore;
