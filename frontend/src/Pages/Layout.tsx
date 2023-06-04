import * as React from 'react';
import {
    CDBSidebar,
    CDBSidebarContent,
    CDBSidebarFooter,
    CDBSidebarHeader,
    CDBSidebarMenu,
    CDBSidebarMenuItem
}from "cdbreact";
import { NavLink } from "react-router-dom";
import {LoggedInContextCookie} from "../Authentication/Authn";
import {useEffect, useState} from "react";
import {Fetch} from "../Utils/useFetch";
import { MDBDropdown, MDBDropdownMenu, MDBDropdownToggle, MDBDropdownItem } from 'mdb-react-ui-kit';
import 'bootstrap/dist/css/bootstrap.css';
import Dropdown from 'react-bootstrap/Dropdown';

export function Layout() {
    const [isLogout, setIsLogout] = useState<boolean>(false)
    const check = React.useContext(LoggedInContextCookie).loggedInState.auth
    console.log("Check", check)

    useEffect(() => {
    }, [check]);


    function handleLogout(){
        setIsLogout(true)
    }

    function FetchLogout(){
        Fetch("/user/logout", 'POST')
        setIsLogout(false)
        window.location.href = "/"
        return <></>
    }

    return (
        <div>
            {!isLogout ?
                <div
                    style={{display: "flex", height: "100vh", overflow: "scroll initial"}}
                >
                    <CDBSidebar textColor="#fff" backgroundColor="#333" className={""} breakpoint={0} toggled={false}
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
                            {!check ?
                                <CDBSidebarMenu>
                                    <NavLink to="/login">
                                        <CDBSidebarMenuItem>Login</CDBSidebarMenuItem>
                                    </NavLink>
                                </CDBSidebarMenu> :
                                <CDBSidebarMenu>
                                    <NavLink to="/">
                                        <CDBSidebarMenuItem onClick={handleLogout}>Logout</CDBSidebarMenuItem>
                                    </NavLink>
                                </CDBSidebarMenu>
                            }
                        </CDBSidebarFooter>
                    </CDBSidebar>
                </div>
                : <FetchLogout/>
            }
        </div>
    );
}


export function LayoutRole(){
    const check = React.useContext(LoggedInContextCookie).loggedInState.role
    console.log("CHECK",check)
    const role = (check !== "client" && check !== "guest")

    return (
        <div
            style={{
                alignContent : 'right',
                display: "flex",
                height: "100vh",
                overflow: "scroll initial",
                justifyContent: "flex-end",
                right: '0'
            }}
        >
            {role ?
                <CDBSidebar
                    textColor="#fff"
                    backgroundColor="#333"
                    className=""
                    breakpoint={0}
                    toggled={false}
                    minWidth=""
                    maxWidth=""
                    style={{ marginLeft: 'auto' }}
                >
                    <CDBSidebarHeader prefix={<i className="fa fa-bars fa-large"></i>}>
                        <a
                            href="/"
                            className="text-decoration-none"
                            style={{color: "inherit"}}
                        >
                            Your companies
                        </a>
                    </CDBSidebarHeader>

                    <CDBSidebarContent className="sidebar-content">
                        <CDBSidebarMenu>
                            <MyDropdownButton/>
                            <NavLink to="/new/company">
                                <CDBSidebarMenuItem icon="table">Company</CDBSidebarMenuItem>
                            </NavLink>
                            <NavLink to="/profile">
                                <CDBSidebarMenuItem icon="user">Profile</CDBSidebarMenuItem>
                            </NavLink>
                        </CDBSidebarMenu>
                    </CDBSidebarContent>
                </CDBSidebar> :
                <></>
            }
        </div>
    );
}


function MyDropdownButton() {
    const [items, setItems] = useState([]);
    const [managerCompanies,setManagerCompanies] = useState(false)

    const handleButtonClick = () => {
        setManagerCompanies(true)
    };

    useEffect(() => {
    }, [managerCompanies])

    const response = Fetch("/company/info?role=MANAGER",'GET')

    console.log("Layout",response)
    return (
        <div>
            <Dropdown>
                <Dropdown.Toggle style={{background :'#8faacf'}} variant="success">
                    Open Menu
                </Dropdown.Toggle >
                <Dropdown.Menu>
                    {response.response ?
                        <div>
                            {response.response.map((object: any) => (
                                <Dropdown.Item href="#" id={object.id}>
                                    {object.name}
                                </Dropdown.Item>))}
                        </div>
                        : <Dropdown.Item disabled>No companies</Dropdown.Item>
                    }
                </Dropdown.Menu>
            </Dropdown>
        </div>
    )

}

