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
import "../Style/LayoutCompany.css"
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
                <LayoutCompany/> :
                <></>
            }
        </div>
    );
}

function LayoutCompany() {
    return (
        <div className="container">
            <div className="row">
                <div className="col-md-6">
                    <div className="navbar">
                        <ul className="menu">
                            <li><a href="#" className="fa fa-facebook"></a></li>
                            <li><a href="#" className="fa fa-twitter"></a></li>
                            <li><a href="#" className="fa fa-youtube"></a></li>
                            <li><a href="#" className="fa fa-google-plus"></a></li>
                            <li><a href="#" className="fa fa-linkedin"></a></li>
                            <li><a href="#" className="fa fa-instagram"></a></li>
                            <li><a href="#" className="fa fa-pinterest"></a></li>
                            <li><a href="#" className="fa fa-rss"></a></li>
                            <li><a href="#" className="fa fa-github"></a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

    );
}




function MyDropdownButton(props:{role:String,titleButton:String}) {
    const [items, setItems] = useState([]);
    const [managerCompanies,setManagerCompanies] = useState(false)

    const handleButtonClick = () => {
        setManagerCompanies(true)
    };

    useEffect(() => {
    }, [managerCompanies])

    const response = Fetch(`/company/info?role=${props.role}`,'GET')

    console.log("Layout",response)
    return (
        <div style={{ display: 'flex', justifyContent: 'center' }}>
            <Dropdown>
                <Dropdown.Toggle style={{background :'#8faacf', borderColor: '#8faacf'}} variant="success">
                    {props.titleButton}
                </Dropdown.Toggle >
                <Dropdown.Menu>
                    {response.response ?
                        <div>
                            {response.response.map((object: any) => (
                                <Dropdown.Item style={{ color: 'black' }} href="#" id={object.id}>
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

