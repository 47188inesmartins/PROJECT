import Navbar from "react-bootstrap/Navbar";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import React from "react";
import {
    CDBSidebar,
    CDBSidebarContent,
    CDBSidebarFooter,
    CDBSidebarHeader,
    CDBSidebarMenu,
    CDBSidebarMenuItem
} from "cdbreact";
import { NavLink } from "react-router-dom";
import "@trendmicro/react-sidenav/dist/react-sidenav.css";

export function Layout() {
    return (
        <div
            style={{ display: "flex", height: "100vh", overflow: "scroll initial" }}
        >
            <CDBSidebar textColor="#fff" backgroundColor="#333" className={""} breakpoint={0} toggled={false}
                        minWidth={""} maxWidth={""}>
                <CDBSidebarHeader prefix={<i className="fa fa-bars fa-large"></i>}>
                    <a
                        href="/"
                        className="text-decoration-none"
                        style={{ color: "inherit" }}
                    >
                        Schedule it
                    </a>
                </CDBSidebarHeader>

                <CDBSidebarContent className="sidebar-content">
                    <CDBSidebarMenu>
                        <NavLink to="/user/1/appointments">
                            <CDBSidebarMenuItem icon="columns">Appointments</CDBSidebarMenuItem>
                        </NavLink>
                        <NavLink to="/new/company">
                            <CDBSidebarMenuItem icon="table">Company</CDBSidebarMenuItem>
                        </NavLink>
                        <NavLink to="/profile">
                            <CDBSidebarMenuItem icon="user">Profile</CDBSidebarMenuItem>
                        </NavLink>
                    </CDBSidebarMenu>
                </CDBSidebarContent>

                <CDBSidebarFooter>
                    <CDBSidebarMenu>
                        <NavLink to="/analytics">
                            <CDBSidebarMenuItem icon="chart-line">Login</CDBSidebarMenuItem>
                        </NavLink>
                    </CDBSidebarMenu>
                </CDBSidebarFooter>
            </CDBSidebar>
        </div>
    );


    /* return (
       <>
            <br />
            <Navbar bg="light" variant="light">
                <Container>
                    <Navbar.Brand href="#home">Schedule it</Navbar.Brand>
                    <Nav className="me-auto">
                        <Nav.Link href="#home">Home</Nav.Link>
                        <Nav.Link href="#features">Features</Nav.Link>
                        <Nav.Link href="#pricing">Pricing</Nav.Link>
                    </Nav>
                    <Nav className="ms-auto">
                        <Nav.Link href="/login">Login</Nav.Link>
                    </Nav>
                </Container>
            </Navbar>
        </>
    );*/
}
