import * as React from "react"
import {
    MDBCol,
    MDBContainer,
    MDBRow,
    MDBCard,
    MDBCardText,
    MDBCardBody,
    MDBCardImage,
    MDBBreadcrumb,
    MDBBreadcrumbItem,
} from 'mdb-react-ui-kit'
import {Fetch} from "../Utils/useFetch";
import {useContext} from "react";
import {LoggedInContextCookie} from "../Authentication/Authn";
import {useParams} from "react-router-dom";
import AdvancedCalendar from "./AdvancedCalendar";
import {Layout} from "./Layout";


export function CompanyProfileManaging() {

    const token = useContext(LoggedInContextCookie).loggedInState.role

    const params = useParams()
    const id = params.id


    const check = false// (token !== "GUEST" && token !== "CLIENT")
    const response = Fetch(`/company/${id}`,'GET')
    const containerStyle = {
        display: 'flex',
    };

    const contentStyle = {
        marginLeft: '200px',
        backgroundColor: 'darkslategray',
        padding: '20px',
    };

    return (
        <div  style = {contentStyle}>
            <div className="sidebar-left" >
                <Layout />
            </div>
            <div  >
            {
                !response.response?
                    <div className="loading">
                        <div className="spinner-border" role="status">
                            <span className="sr-only">Loading...</span>
                        </div>
                    </div>
                    :
                    <section style={{ backgroundColor: 'darkslategray' }}>
                        <MDBContainer className="py-5">
                            <MDBRow>
                                <MDBCol>
                                    <MDBBreadcrumb className="bg-light rounded-3 p-3 mb-4">
                                        <MDBBreadcrumbItem>
                                            <a style={{color: "black", flex: 'center'}}>{response.response.name}</a>
                                        </MDBBreadcrumbItem>
                                    </MDBBreadcrumb>
                                </MDBCol>
                            </MDBRow>
                            <MDBRow>
                                <MDBCol lg="4">
                                    <MDBCard className="mb-4">
                                        <MDBCardBody className="text-center">
                                            <MDBCardImage
                                                src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3.webp"
                                                alt="avatar"
                                                className="rounded-circle"
                                                style={{ width: '150px' }}
                                                fluid />
                                        </MDBCardBody>
                                    </MDBCard>
                                    <MDBCard className="mb-4 mb-md-0">
                                        <MDBCardBody>
                                            <a href={`/company/${id}/managing/employees`} className="mb-1"  style={{ fontSize: '1.2rem' }}>
                                                <MDBCardText> Manage employees </MDBCardText>
                                            </a>
                                            <a href={`/company/${id}/managing/vacations`} className="mb-1"  style={{ fontSize: '1.2rem' }}>
                                                <MDBCardText> Manage company's vacations </MDBCardText>
                                            </a>
                                            <a href={`/company/${id}/services`} className="mb-1"  style={{ fontSize: '1.2rem' }}>
                                                <MDBCardText> Manage company's services </MDBCardText>
                                            </a>
                                            <a href={`/company/${id}/services`} className="mb-1"  style={{ fontSize: '1.2rem' }}>
                                                <MDBCardText> Add personal break </MDBCardText>
                                            </a>
                                        </MDBCardBody>
                                    </MDBCard>
                                </MDBCol>
                                <MDBCol lg="8">
                                    <MDBCard className="mb-4">
                                        <MDBCardBody>
                                            <MDBRow>
                                                <MDBCol sm="3">
                                                    <MDBCardText>Full Name</MDBCardText>
                                                </MDBCol>
                                                <MDBCol sm="9">
                                                    <MDBCardText className="text-muted">{response.response.name}</MDBCardText>
                                                </MDBCol>
                                            </MDBRow>
                                            <hr />
                                            <MDBRow>
                                                <MDBCol sm="3">
                                                    <MDBCardText>Address</MDBCardText>
                                                </MDBCol>
                                                <MDBCol sm="9">
                                                    <MDBCardText className="text-muted">{response.response.address}</MDBCardText>
                                                </MDBCol>
                                            </MDBRow>
                                            <hr/>
                                            <MDBRow>
                                                <MDBCol sm="3">
                                                    <MDBCardText>Type</MDBCardText>
                                                </MDBCol>
                                                <MDBCol sm="9">
                                                    <MDBCardText className="text-muted">{response.response.type}</MDBCardText>
                                                </MDBCol>
                                            </MDBRow>
                                            <hr />
                                            <MDBRow>
                                                <MDBCol sm="3">
                                                    <MDBCardText>Description</MDBCardText>
                                                </MDBCol>
                                                <MDBCol sm="9">
                                                    <MDBCardText className="text-muted">{response.response.description}</MDBCardText>
                                                </MDBCol>
                                            </MDBRow>
                                            <hr />
                                            <MDBRow>
                                                <MDBCol sm="3">
                                                    <MDBCardText>Service</MDBCardText>
                                                </MDBCol>
                                                {response.response.service.map((object: any) => (
                                                    <MDBCol sm="9">
                                                        <MDBCardText>{object.name}</MDBCardText>
                                                    </MDBCol>
                                                ))}
                                            </MDBRow>
                                        </MDBCardBody>
                                    </MDBCard>
                                    {check?
                                        <MDBRow>
                                            <MDBCol md="6">
                                                <MDBCard className="mb-4 mb-md-0">
                                                    <MDBCardBody>
                                                        <MDBCardText className="mb-4">Services</MDBCardText>
                                                        {response.response.service.map((object: any) => (
                                                            <div className="d-flex justify-content-between align-items-center">
                                                                <a style={{ fontSize: '1.2rem' }}>
                                                                    <MDBCardText>{object.name}</MDBCardText>
                                                                </a>
                                                                <a href={`/service/${object.id}/schedule`} className="mb-1" >
                                                                    <MDBCardText>Edit</MDBCardText>
                                                                </a>
                                                            </div>
                                                        ))}
                                                    </MDBCardBody>
                                                </MDBCard>
                                            </MDBCol>
                                            <MDBCol md="6">
                                                <MDBCard className="mb-4 mb-md-0">
                                                    <MDBCardBody>
                                                        <MDBCardText className="mb-4">Check {response.response.name}'s schedule</MDBCardText>
                                                        <a href={`/company/${id}/managing`} className="mb-1" >
                                                            <MDBCardText>Schedule</MDBCardText>
                                                        </a>
                                                    </MDBCardBody>
                                                </MDBCard>
                                            </MDBCol>
                                        </MDBRow>:
                                        <></>
                                    }
                                </MDBCol>
                            </MDBRow>
                        </MDBContainer>
                        <div style={{ margin:'20px'}}>
                        <AdvancedCalendar />
                    </div>
                    </section>
            }

        </div></div>
    );
}

