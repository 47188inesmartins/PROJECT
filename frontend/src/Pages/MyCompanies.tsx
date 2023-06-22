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


export function MyCompanies() {
    const token = useContext(LoggedInContextCookie).loggedInState.role

    const check = true //(token !== "GUEST" && token !== "CLIENT")
    const response = Fetch('/user/info','GET')

    return (
        <div>
            {!response.response?
                <div className="loading">
                    <div className="spinner-border" role="status">
                        <span className="sr-only">Loading...</span>
                    </div>
                </div>
                :
                <section style={{ backgroundColor: '#eee' }}>
                    <MDBContainer className="py-5">
                        <MDBRow>
                            <MDBCol>
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
                            { resp.response.map((object: any) => (
                                <>
                                    {props.role == 'MANAGER'?
                                        <a href={`/company/${object.id}/managing`} className="mb-1"  style={{ fontSize: '1.2rem' }}>
                                            <MDBCardText>{object.name}</MDBCardText>
                                        </a>
                                    :
                                        <a href={`/company/${object.id}/employing`} className="mb-1"  style={{ fontSize: '1.2rem' }}>
                                            <MDBCardText>{object.name}</MDBCardText>
                                        </a>
                                    }
                                </>
                            ))}
                        </MDBCardBody>
                    </MDBCard>
                </MDBCol>
            }
        </>
    );
}

