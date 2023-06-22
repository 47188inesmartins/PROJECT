import * as React from 'react';
import {
    CDBSidebar,
    CDBSidebarContent,
    CDBSidebarHeader,
    CDBSidebarMenu,
    CDBSidebarMenuItem
}from "cdbreact";
import { NavLink } from "react-router-dom";
import {LoggedInContextCookie} from "../Authentication/Authn";
import {useEffect, useState} from "react";
import {Fetch} from "../Utils/useFetch";
import 'bootstrap/dist/css/bootstrap.css';
import "../Style/LayoutCompany.css"

export function Layout() {

    const context = React.useContext(LoggedInContextCookie).loggedInState
    const auth = context.auth
    const role = context.role
    console.log(role)

    const hasManager = role.some((user) => user.role === "MANAGER");
    const hasEmployee = role.some((user) => user.role === "EMPLOYEE");

    console.log("has manager = ", hasManager, hasEmployee)

    return (
        <div>
            <div
                style={{display: "flex", height: "100vh", overflow: "scroll initial"}}
            >
                <CDBSidebar textColor="#fff" backgroundColor="black" className={""} breakpoint={0} toggled={false}
                            minWidth={""} maxWidth={""}>
                    <CDBSidebarHeader prefix={<i className="fa fa-bars fa-large"></i>}>
                        <a
                            href="/"
                            className="text-decoration-none"
                            style={{color: "inherit"}}
                        >
                            Schedule it
                        </a>
                    </CDBSidebarHeader>
                    {auth ?
                        <div>
                            <CDBSidebarContent className="sidebar-content">
                                <CDBSidebarMenu>
                                    <NavLink to="/user/appointments">
                                        <CDBSidebarMenuItem icon="columns">Appointments</CDBSidebarMenuItem>
                                    </NavLink>
                                    {hasManager || hasEmployee?
                                        <NavLink to="/my/companies">
                                            <CDBSidebarMenuItem icon="table">My Companies</CDBSidebarMenuItem>
                                        </NavLink>
                                        :
                                        <></>
                                    }
                                    <NavLink to="/user/profile">
                                        <CDBSidebarMenuItem icon="user">Profile</CDBSidebarMenuItem>
                                    </NavLink>
                                </CDBSidebarMenu>
                            </CDBSidebarContent>
                            <div style={{ position: 'absolute', bottom: '0', width: '100%' }}>
                                <CDBSidebarMenu>
                                    <NavLink to="/logout">
                                        <CDBSidebarMenuItem>Logout</CDBSidebarMenuItem>
                                    </NavLink>
                                </CDBSidebarMenu>
                            </div>
                        </div>
                        :
                        <div>
                            <CDBSidebarContent className="sidebar-content">
                                <CDBSidebarMenu>
                                    <NavLink to="/login">
                                        <CDBSidebarMenuItem icon="columns">Appointments</CDBSidebarMenuItem>
                                    </NavLink>
                                    <NavLink to="/login">
                                        <CDBSidebarMenuItem icon="user">Profile</CDBSidebarMenuItem>
                                    </NavLink>
                                </CDBSidebarMenu>
                            </CDBSidebarContent>
                            <div style={{ position: 'absolute', bottom: '0', width: '100%' }}>
                                <CDBSidebarMenu>
                                    <NavLink to="/signup">
                                        <CDBSidebarMenuItem>Register your company</CDBSidebarMenuItem>
                                    </NavLink>
                                </CDBSidebarMenu>
                                <CDBSidebarMenu>
                                    <NavLink to="/login">
                                        <CDBSidebarMenuItem>Login</CDBSidebarMenuItem>
                                    </NavLink>
                                </CDBSidebarMenu>
                            </div>
                        </div>
                        }
                </CDBSidebar>
            </div>
        </div>
    );
}