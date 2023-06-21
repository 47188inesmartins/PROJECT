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

    const auth = React.useContext(LoggedInContextCookie).loggedInState.auth

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
                                    <NavLink to="/new/company">
                                        <CDBSidebarMenuItem icon="table">Company</CDBSidebarMenuItem>
                                    </NavLink>
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
                                    <NavLink to="/new/company">
                                        <CDBSidebarMenuItem icon="table">Company</CDBSidebarMenuItem>
                                    </NavLink>
                                    <NavLink to="/login">
                                        <CDBSidebarMenuItem icon="user">Profile</CDBSidebarMenuItem>
                                    </NavLink>
                                </CDBSidebarMenu>
                            </CDBSidebarContent>
                            <div style={{ position: 'absolute', bottom: '0', width: '100%' }}>
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