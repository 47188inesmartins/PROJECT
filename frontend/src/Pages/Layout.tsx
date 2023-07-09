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
import {useState} from "react";
import {Fetch} from "../Utils/useFetch";
import 'bootstrap/dist/css/bootstrap.css';
import "../Style/LayoutCompany.css"
import { Dropdown, DropdownButton } from 'react-bootstrap';


export function Layout() {

    const context = React.useContext(LoggedInContextCookie).loggedInState
    const auth = context.auth
    const role = context.role
    console.log(typeof role)


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
                                    <NavLink to="/signup?mode=business">
                                        <CDBSidebarMenuItem>For business</CDBSidebarMenuItem>
                                    </NavLink>
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
                                    <NavLink to="/signup?mode=business">
                                        <CDBSidebarMenuItem>For business</CDBSidebarMenuItem>
                                    </NavLink>
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


export function LayoutRight() {

    const context = React.useContext(LoggedInContextCookie).loggedInState
    const role = context.role
    const hasManager = role.some((user) => user.role === "MANAGER");
    const hasEmployee = role.some((user) => user.role === "EMPLOYEE");

    const managingCompanies = Fetch(`/company/info?role=MANAGER`, 'GET')
    console.log("managing companies ", managingCompanies)
    if(managingCompanies.response){
        if(managingCompanies.response.length === 0){
            return <></>
        }
    }

    return (
        <div className="sidebar-right">
            <CDBSidebar textColor="#fff" backgroundColor="black" className={""} breakpoint={0} toggled={false} minWidth={""} maxWidth={""}>
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
                        {managingCompanies.response ?
                            <CDBSidebarMenu>
                                <div className="dropdown-container">
                                    <DropdownButton style={{backgroundColor: "black"}} title="Owned companies"
                                                    className="dropdown-button">
                                        {managingCompanies.response.map((object: any) => (
                                            <a href={`/company/${object.id}/managing`} className="mb-1" style={{fontSize: '1.2rem'}}>
                                                <Dropdown.Item>{object.name}</Dropdown.Item>
                                            </a>
                                        ))}
                                    </DropdownButton>
                                </div>
                            </CDBSidebarMenu>
                            :
                            <></>
                        }
                    </CDBSidebarContent>
                </div>
            </CDBSidebar>
        </div>
    );
}