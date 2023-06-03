import * as React from "react";
import {useState} from "react";
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
import {Button, Modal} from "react-bootstrap";
import {Navigate} from "react-router";
import {Fetch} from "../Utils/useFetch";


export function MyAppointments() {
    const params = useParams()
    const [cancel, setCancel] = useState(false);
    const [idAppointment,setIdAppointment] = useState<undefined|Number>(undefined)
    const id = params.id
    const appointments = Fetch(`/user/${id}/appointments`,"GET")
    console.log(appointments)
    const handleCancel = (idApp:Number) =>{
        setCancel(true);
        setIdAppointment(idApp);
    }

    return (
            <div>
                {!appointments.response?
                    <div className="loading">
                        <div className="spinner-border" role="status">
                            <span className="sr-only">Loading...</span>
                        </div>
                    </div>:(
                        <section className="vh-100" style={{ backgroundColor: '#0e4378' }}>
                            {cancel?<PopUpMessage id={idAppointment}/>
                                :<MDBContainer className="py-5 h-100">
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
                                                                        <button style={{backgroundColor:'#d14319'}} className="btn btn-outline-light btn-lg px-5" type="button" onClick={() => handleCancel(appointment.id)}>Cancel</button>
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
                            }</section>
                    )}
            </div>
    );
}

function PopUpMessage(props:{id:Number|undefined}) {
    const [show, setShow] = useState(true);
    const [cancel, setCancel] = useState(false);
    const params = useParams()
    const id = params.id

    const handleClose = () => setShow(false);
    const handleCancel = () => setCancel(true);

    if(props.id == undefined) return <Navigate to = {`/user/${id}/appointments`} replace={true}></Navigate>
    return (
        <>
            {!cancel?
                <>
                    <Modal
                        show={show}
                        onHide={handleClose}
                        backdrop="static"
                        keyboard={false}
                    >
                        <Modal.Header closeButton>
                            <Modal.Title>Cancel appointment</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            Are you sure you want to cancel your appointment?
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant="secondary" onClick={handleClose}>
                                Close
                            </Button>
                            <Button variant="primary" onClick = {handleCancel}>Cancel</Button>
                        </Modal.Footer>
                    </Modal>
                </>:
                <FetchCancelAppointment id={props.id}/>
            }
        </>
    );
}


function FetchCancelAppointment(props:{id:Number}){
    const params = useParams()
    const id = params.id

    Fetch('/appointment/'+props.id,
        'DELETE')
    window.location.href = `/user/${id}/appointments`;
    return(<> </>);
}