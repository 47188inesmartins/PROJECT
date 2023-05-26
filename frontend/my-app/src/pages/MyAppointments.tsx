import React, {useState} from "react";
import {
    MDBBtn,
    MDBCard,
    MDBCardBody,
    MDBCheckbox,
    MDBCol,
    MDBContainer,
    MDBInput,
    MDBRow,
    MDBTable, MDBTableBody, MDBTableHead
} from "mdb-react-ui-kit";
import {Form, useParams} from "react-router-dom";
import {Fetch} from "../useFetch";
import {Button, Card, Col, Row} from "react-bootstrap";
import Container from "react-bootstrap/Container";

export function MyAppointments1() {
    const params = useParams()
    const id = params.id
    const appointments = Fetch(`/user/${id}/appointments`,"GET")
    return (
        <div>
        {!appointments.response?
            <div className="loading">
            <div className="spinner-border" role="status">
                <span className="sr-only">Loading...</span>
            </div>
            </div>:(
                <div>
                <div>
                    {appointments.response.futureAppointments.map((appointment: any)=> (
                        <Card key={appointment.id} style={{ width: '18rem' ,backgroundColor:'blue' }}>
                            <Card.Body>
                                <Card.Title>{appointment.companyName}</Card.Title>
                                <Card.Subtitle className="mb-2 text-muted">Scheduled with {appointment.employee}</Card.Subtitle>
                                <Card.Text>
                                    Scheduled for {appointment.appDate} at {appointment.appHour}
                                </Card.Text>
                            </Card.Body>
                        </Card>
                    ))}
                </div>
            <div>
                {appointments.response.passedAppointments.map((appointment: any)=> (
                        <Card key={appointment.id} style={{ width: '18rem' ,backgroundColor:'pink' }}>
                            <Card.Body>
                            <Card.Title>{appointment.companyName}</Card.Title>
                                <Card.Subtitle className="mb-2 text-muted">Scheduled with {appointment.employee}</Card.Subtitle>
                                    <Card.Text>
                                    Scheduled for {appointment.appDate} at {appointment.appHour}
                                    </Card.Text>
                            </Card.Body>
                        </Card>
                        ))}
                    </div>
                </div>
            )
        }
        </div>
    );
}

export function MyAppointments() {
    const params = useParams()
    const id = params.id
    const appointments = Fetch(`/user/${id}/appointments`,"GET")
    return (
        <div>
            {!appointments.response?
                <div className="loading">
                    <div className="spinner-border" role="status">
                        <span className="sr-only">Loading...</span>
                    </div>
                </div>:(
        <section className="vh-100" style={{ backgroundColor: '#0e4378' }}>
            <MDBContainer className="py-5 h-100">
                <MDBRow className="d-flex justify-content-center align-items-center">
                    <MDBCol lg="9" xl="7">
                        <MDBCard className="rounded-3">
                            <MDBCardBody className="p-4">
                                <h4 className="text-center my-3 pb-3">My Appointments</h4>
                                <MDBRow className="row-cols-lg-auto g-3 justify-content-center align-items-center mb-4 pb-2">
                                    <MDBCol size="12">
                                        <MDBInput
                                            id="form1"
                                            type="text"
                                            placeholder="Search for a company"
                                        />
                                    </MDBCol>
                                    <MDBCol size="12">
                                        <button style={{ backgroundColor: '#0e4378' }} className="btn btn-outline-light btn-lg px-2" type="button" onClick={()=>{}}>Search</button>
                                    </MDBCol>
                                </MDBRow>
                                <MDBTable className="mb-4">
                                    <MDBTableHead>
                                        <tr>
                                            <th scope="col">Company</th>
                                            <th scope="col">Employee</th>
                                            <th scope="col">Date</th>
                                            <th scope="col"></th>
                                        </tr>
                                    </MDBTableHead>
                                    <MDBTableBody>
                                        {appointments.response.futureAppointments.map((appointment: any)=> (
                                        <tr>
                                            <th scope="row">{appointment.companyName}</th>
                                            <td>{appointment.employee}</td>
                                            <td>{appointment.appDate} at {appointment.appHour}</td>
                                            <td>
                                                <button style={{backgroundColor:'#d14319'}} className="btn btn-outline-light btn-lg px-5" type="button" onClick={()=>{}}>Cancel</button>
                                            </td>
                                        </tr>
                                        ))}
                                        {appointments.response.passedAppointments.map((appointment: any)=> (
                                            <tr>
                                                <th scope="row">{appointment.companyName}</th>
                                                <td>{appointment.employee}</td>
                                                <td>{appointment.appDate} at {appointment.appHour}</td>
                                                <td>
                                                    <button style={{backgroundColor:'#F0A818'}} className="btn btn-outline-light btn-lg px-5" type="button" onClick={()=>{}}>Delete</button>
                                                </td>
                                            </tr>
                                        ))}
                                    </MDBTableBody>
                                </MDBTable>
                            </MDBCardBody>
                        </MDBCard>
                    </MDBCol>
                </MDBRow>
            </MDBContainer>
        </section>
                )}
        </div>
    );
}

