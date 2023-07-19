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
import {Fetch} from "../../Utils/useFetch";
import {useContext} from "react";
import {Layout, LayoutRight} from "../../Pages/Layout";
import {LoggedInContextCookie} from "../Authentication/Authn";

export function ProfilePage() {

    const role = useContext(LoggedInContextCookie).loggedInState.role
    const roleCheck = ((role.indexOf('manager') === -1) && (role.indexOf('employee') === -1))
    const response = Fetch('/user/info','GET')
    console.log("piccc",response.response)

    const contentStyle = {
        marginLeft: '200px',
        backgroundColor: '#EFEEEE',
        padding: '20px',
    };

    return (
        <div style = {contentStyle}>
            <div className="sidebar-left" >
                <Layout />
            </div>
            {!response.response?
                <div className="loading">
                    <div className="spinner-border" role="status">
                        <span className="sr-only">Loading...</span>
                    </div>
                </div>
                :
                <section style={{  margin: '20px', marginLeft: '0', marginRight: '200px' ,backgroundColor: '#eee' }}>
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

                                        {response.response.profilePic?
                                            <>
                                                <MDBCardImage
                                                src= {`data:image/jpeg;base64,${response.response.profilePic}`}
                                                alt="avatar"
                                                className="rounded-circle"
                                                style={{ width: '150px' }}
                                                fluid />
                                                <p className="text-muted mb-1">{response.response.name}</p>
                                            </>
                                            :  <>
                                                <MDBCardImage
                                                    src= {`https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava${Math.floor(Math.random() * 6) + 1}.webp`}
                                                    alt="avatar"
                                                    className="rounded-circle"
                                                    style={{ width: '150px' }}
                                                    fluid />
                                                <p className="text-muted mb-1">{response.response.name}</p>

                                            </>
                                        }
                                    </MDBCardBody>
                                    <a href="/user/upload-pic" className="edit-profile-link">
                                        <p>Update your profile pic</p>
                                    </a>
                                </MDBCard>
                                    {!roleCheck?
                                        <>
                                            <MDBCard className="mb-4 mb-md-0">
                                                <MDBCardBody>
                                                    <MDBCardText className="mb-4">My wallet </MDBCardText>
                                                    <a href={`/company/profits`} className="mb-1"  style={{ fontSize: '1.2rem' }}>
                                                        <MDBCardText> Check your profits </MDBCardText>
                                                    </a>
                                                </MDBCardBody>
                                            </MDBCard>
                                        </>
                                        :
                                        <></>
                                    }
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
                                        <hr />
                                        <MDBRow>
                                            <MDBCol sm="3">
                                                <MDBCardText>Address</MDBCardText>
                                            </MDBCol>
                                            <MDBCol sm="9">
                                                <MDBCardText className="text-muted">{response.response.address}</MDBCardText>
                                            </MDBCol>
                                        </MDBRow>
                                    </MDBCardBody>
                                </MDBCard>
                            </MDBCol>
                        </MDBRow>
                    </MDBContainer>
                </section>
            }
            <LayoutRight/>
        </div>
    );
}

function CompanyRole(props:{role:String,text:String}) {
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
                            <MDBCardText className="mb-4">{props.text}</MDBCardText>
                            { resp.response.map((object: any) => (
                                <a href={`/company/${object.id}/profile`} className="mb-1"  style={{ fontSize: '1.2rem' }}>
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

