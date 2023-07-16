import * as React from 'react';
import {
    CDBSidebar,
    CDBSidebarContent,
    CDBSidebarHeader,
    CDBSidebarMenu,
    CDBSidebarMenuItem
}from "cdbreact";
import { NavLink } from "react-router-dom";
import {LoggedInContextCookie} from "./Authentication/Authn";
import {Fetch} from "../Utils/useFetch";
import 'bootstrap/dist/css/bootstrap.css';
import "../Style/LayoutCompany.css"
import { Dropdown, DropdownButton } from 'react-bootstrap';
import Cookies from 'js-cookie';


export function Layout() {

    const context = React.useContext(LoggedInContextCookie).loggedInState
    const auth = Cookies.get('name')
    const role =  context.role;
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
                            href="/?page=0&size=3"
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
                                    <NavLink to="/new/company">
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
    const employingCompanies = Fetch(`/company/info?role=EMPLOYEE`, 'GET')


    console.log("managing companyes = ", managingCompanies)

    const handleDropdownClick = (companyId) => {
        window.location.href = `/company/${companyId}/managing`;
    };

    return (
        <div className="sidebar-right">
            <CDBSidebar textColor="#fff" backgroundColor="black" className={""} breakpoint={0} toggled={false} minWidth={""} maxWidth={""}>
                <div>
                    <CDBSidebarContent className="sidebar-content">
                        {managingCompanies.response?
                            <CDBSidebarMenu>
                                <div className="dropdown-container">
                                    <DropdownButton style={{backgroundColor: "black"}} title="Owned companies"
                                                    className="dropdown-button">
                                        {managingCompanies.response.map((object: any) => (
                                            <Dropdown.Item key={object.id} onClick={() => handleDropdownClick(object.id)}>
                                                {object.name}
                                            </Dropdown.Item>
                                        ))}
                                    </DropdownButton>
                                </div>
                            </CDBSidebarMenu>
                            :
                            <></>
                        }
                        {employingCompanies.response?
                            <>
                                {employingCompanies.response.length !== 0?
                                    <CDBSidebarMenu>
                                        <div className="dropdown-container">
                                            <DropdownButton style={{backgroundColor: "black"}} title="Owned companies"
                                                            className="dropdown-button">
                                                {employingCompanies.response.map((object: any) => (
                                                    <Dropdown.Item key={object.id} onClick={() => handleDropdownClick(object.id)}>
                                                        {object.name}
                                                    </Dropdown.Item>
                                                ))}
                                            </DropdownButton>
                                        </div>
                                    </CDBSidebarMenu>
                                    :
                                    <></>
                            }
                            </>
                            :
                            <></>
                        }
                    </CDBSidebarContent>
                </div>
            </CDBSidebar>
        </div>
    );
}