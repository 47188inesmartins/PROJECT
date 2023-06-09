import * as React from "react"
import {
    MDBCol,
    MDBContainer,
    MDBRow,
    MDBCard,
    MDBCardText,
    MDBCardBody,
    MDBCardImage,
    MDBBtn,
    MDBBreadcrumb,
    MDBBreadcrumbItem,
    MDBProgress,
    MDBProgressBar,
    MDBIcon,
    MDBListGroup,
    MDBListGroupItem
} from 'mdb-react-ui-kit'
import {Fetch} from "../Utils/useFetch";
import {MyCompany} from "./ManagerCompany";
import {useContext} from "react";
import {LoggedInContextCookie} from "../Authentication/Authn";

export function ProfilePage() {
    const token = useContext(LoggedInContextCookie).loggedInState.role
    const check = (token !== "GUEST" && token !== "CLIENT")
    const response = Fetch('/user/info','GET')
    return (
        <div>
            {
                !response.response?
                    <div className="loading">
                        <div className="spinner-border" role="status">
                            <span className="sr-only">Loading...</span>
                        </div>
                    </div> :
                    <section style={{ backgroundColor: '#eee' }}>
                        <MDBContainer className="py-5">
                            <MDBRow>
                                <MDBCol>
                                    <MDBBreadcrumb className="bg-light rounded-3 p-3 mb-4">
                                        <MDBBreadcrumbItem>
                                            <a style={{color: "black", flex: 'center'}}>My Profile</a>
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
                                            <p className="text-muted mb-1">{response.response.name}</p>
                                            <div className="d-flex justify-content-center mb-2">
                                            </div>
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
                                                    <MDBCardText>Email</MDBCardText>
                                                </MDBCol>
                                                <MDBCol sm="9">
                                                    <MDBCardText className="text-muted">{response.response.email}</MDBCardText>
                                                </MDBCol>
                                            </MDBRow>
                                            <hr />
                                            <MDBRow>
                                                <MDBCol sm="3">
                                                    <MDBCardText>Birthday</MDBCardText>
                                                </MDBCol>
                                                <MDBCol sm="9">
                                                    <MDBCardText className="text-muted">{response.response.birthday}</MDBCardText>
                                                </MDBCol>
                                            </MDBRow>
                                        </MDBCardBody>
                                    </MDBCard>
                                        {check?
                                            <MDBRow>
                                                <CompanyRole role={"MANAGER"} Text={"My managing companies"}/>
                                                <CompanyRole role={"EMPLOYEE"} Text={"Works for"}/>
                                            </MDBRow>:
                                            <></>
                                        }
                                </MDBCol>
                            </MDBRow>
                        </MDBContainer>
                    </section>
            }
        </div>
    );
}

function CompanyRole(props:{role:String,Text:String}) {
    const resp = Fetch(`/company/info?role=${props.role}`, 'GET')

    console.log("companies",resp.response)
    if(resp.response){
        if(resp.response.length === 0){
            return <></>
        }
    }
    return(
        <>
            {!resp.response?
                <div className="loading">
                    <div className="spinner-border" role="status">
                        <span className="sr-only">Loading...</span>
                    </div>
                </div>
                :
                <MDBCol md="6">
                    <MDBCard className="mb-4 mb-md-0">
                        <MDBCardBody>
                            <MDBCardText className="mb-4">{props.Text}</MDBCardText>
                            {resp.response.map((object: any) => (
                                <a href={`/company/${object.id}/managing`} className="mb-1"  style={{ fontSize: '1.2rem' }}>
                                    <MDBCardText>{object.name}</MDBCardText>
                                </a>
                            ))}
                        </MDBCardBody>
                    </MDBCard>
                </MDBCol>
            }
        </>
    );
}
