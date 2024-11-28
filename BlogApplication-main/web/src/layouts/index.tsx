
import NavigationBar from "@/components/NavigationBar.tsx";
import { Outlet } from "react-router-dom";
import { Container } from "@mui/material";

export default function Layout() {
    return (
        <>
            <NavigationBar />
            <Container>
                <Outlet />
            </Container>
        </>
    );
}
