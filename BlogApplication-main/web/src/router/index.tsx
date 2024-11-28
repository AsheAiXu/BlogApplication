import { createBrowserRouter, RouteObject } from "react-router-dom";
import ShowArticlePage from "@/pages/ShowArticlePage.tsx";

const routes: RouteObject[] = [
    {
        path: "/",
        lazy: async () => {
            const Layout = await import("@/layouts");
            return { Component: Layout.default };
        },
        children: [
            {
                index: true,
                lazy: async () => {
                    const Home = await import("@/pages/HomePage");
                    return { Component: Home.default };
                },
            },
            {
                path: "/articles/:id",
                element: <ShowArticlePage />,
            },
            {
                path: "/articles/:id/edit",
                lazy: async () => {
                    const EditArticle = await import(
                        "@/pages/EditArticlePage.tsx"
                    );
                    return { Component: EditArticle.default };
                },
            },
            {
                path: "/articles/new",
                lazy: async () => {
                    const CreateArticle = await import(
                        "@/pages/CreateArticlePage"
                    );
                    return { Component: CreateArticle.default };
                },
            },
            {
                path: "/login",
                lazy: async () => {
                    const LoginPage = await import("@/pages/LogInPage");
                    return { Component: LoginPage.default };
                },
            },
            {
                path: "/register",
                lazy: async () => {
                    const RegisterPage = await import(
                        "@/pages/RegisterPage.tsx"
                    );
                    return { Component: RegisterPage.default };
                },
            },
            {
                path: "*",
                lazy: async () => {
                    const NotFoundPage = await import("@/pages/NotFoundPage");
                    return { Component: NotFoundPage.default };
                },
            },
        ],
    },
];
const router = createBrowserRouter(routes);

export default router;
