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
    MDBBreadcrumbItem, MDBDropdown, MDBDropdownToggle, MDBDropdownMenu, MDBDropdownItem, MDBInput,
} from 'mdb-react-ui-kit'
import {Fetch} from "../Utils/useFetch";
import {useContext, useState} from "react";
import {LoggedInContextCookie} from "./Authentication/Authn";
import {useParams} from "react-router-dom";
import AdvancedCalendar from "./AdvancedCalendar";
import {Layout, LayoutRight} from "./Layout";
import {Button, Dropdown, DropdownButton} from "react-bootstrap";
import {Modal} from "react-bootstrap";
import {Navigate} from "react-router";
import {format} from "date-fns";
import {Appointment} from "../Service/Appointment";

export function CompanyProfileManaging() {
    const params = useParams()
    const id = params.id
    const role = useContext(LoggedInContextCookie).loggedInState.role
    console.log("ROLEE",role)
    const roleCheckManager = role.some(r => (r.companyId === parseInt(id) && r.role === 'MANAGER'))
    console.log('Check',roleCheckManager)
    const [click,SetClick] = useState(false)
    const [show, setShow] = useState(true);
    const handleCancel = () => {
        setShow(false);
        window.location.href = `/company/${id}/profile`;
    }

    function GetEmployees(){
        const response  = Fetch(`/company/${id}/employees`,'GET')
        const services  = Fetch(`/company/${id}/services`,'GET')
        const userInfo = Fetch('user/info','GET')
        const [selectedDate, setSelectedDate] = useState("");
        const [selectedTime, setSelectedTime] = useState("");
        const [selectedEmployee, setSelectedEmployee] = useState("");
        const [selectedService, setSelectedService] = useState("");
        const [appointmentData, setAppointmentData] = useState({
            appHour: "",
            appDate: "",
            userId: null,
            service: null,
        });
        const [submmit,SetSubmmit] = useState(false)

        console.log("APPOIT",appointmentData)
        console.log("EMPLOYEE",selectedEmployee)

        const handleDateSelect = (date) => {
            const getDate = format(new Date(date.target.value), 'yyyy-MM-dd')
            setSelectedDate(getDate)
            setAppointmentData((prevData) => ({
                ...prevData,
                appDate:getDate ,
            }));
        };

        const handleTimeSelect = (time) => {
            const getTime = time.target.value
            console.log("DATAAAA",getTime)
            setSelectedTime(getTime)
            setAppointmentData((prevData) => ({
                ...prevData,
                appHour: getTime,
            }));
        };

        const handleEmployeeSelect = (employee) => {
            setSelectedEmployee(employee.name)
            setAppointmentData((prevData) => ({
                ...prevData,
                userId: employee.id,
            }));
        };

        const handleServiceSelect = (service) => {
            setSelectedService(service.serviceName)
            setAppointmentData((prevData) => ({
                ...prevData,
                service: service.id,
            }));
        }

        function handleAddAppointment(){
            SetSubmmit(true)
        }

        return<div>{submmit?
                <>
                    <p style={{color:'black'}}><strong>Your appointment was added</strong></p>
                    <FetchAppointment appointment={appointmentData}/>
                </>:
            <>
            {response.response && services.response?
            <Modal
                show={show}
                onHide={handleCancel}
                backdrop="static"
                keyboard={false}
            >
                <Modal.Header closeButton>
                    <Modal.Title>Choose an employees</Modal.Title>
                </Modal.Header>
                <Modal.Body className="d-flex flex-column align-items-center text-center">
                    <Dropdown>
                        <Dropdown.Toggle
                            variant="primary"
                            id="category-dropdown"
                            className="form-control dropdown-toggle-text"
                        >
                            {selectedEmployee ||"Select an employee"}
                        </Dropdown.Toggle>
                        {roleCheckManager ?
                            <Dropdown.Menu>
                                {response.response.map((employee) => (
                                    <Dropdown.Item
                                        key={employee.id}
                                        onClick={() => handleEmployeeSelect(employee)}
                                    >
                                        {employee.name}
                                    </Dropdown.Item>
                                ))}
                            </Dropdown.Menu> :
                            <Dropdown.Menu>
                                    <Dropdown.Item
                                        key={userInfo.response.id}
                                        onClick={() => handleEmployeeSelect(userInfo.response)}
                                    >
                                        {userInfo.response.name}
                                    </Dropdown.Item>
                            </Dropdown.Menu>
                        }
                    </Dropdown>
                    <Dropdown>
                        <Dropdown.Toggle
                            variant="primary"
                            id="category-dropdown"
                            className="form-control dropdown-toggle-text"
                        >
                            {selectedService ||"Select a Service"}
                        </Dropdown.Toggle>
                        <Dropdown.Menu>
                            {services.response.length === 0 ?
                                <p> No services available</p> :
                                <>
                                    {services.response.map((serv) => (
                                            <Dropdown.Item
                                                key={serv.id}
                                                onClick={() => handleServiceSelect(serv)}
                                            >
                                                {serv.serviceName}
                                            </Dropdown.Item>
                                        ))
                                    }
                                </>
                            }
                        </Dropdown.Menu>
                    </Dropdown>
                    <MDBInput
                        wrapperClass="mb-4 mx-5 w-100"
                        labelClass="text-white"
                        label="Date"
                        id="dateInput"
                        type="date"
                        size="lg"
                        value={selectedDate}
                        onChange={handleDateSelect}
                    />

                    <MDBInput
                        wrapperClass="mb-4 mx-5 w-100"
                        labelClass="text-white"
                        label="Time"
                        id="timeInput"
                        type="time"
                        size="lg"
                        value={selectedTime}
                        onChange={handleTimeSelect}
                    />
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" onClick={handleAddAppointment}>Add appointment</Button>
                    <Button variant="primary" onClick={handleCancel}>Cancel</Button>
                </Modal.Footer>
            </Modal>
                : <p>Loading</p>
            }</>
        }</div>
    }

    const handleClick = () =>{
        SetClick(true)
    }
    const check = false// (token !== "GUEST" && token !== "CLIENT")
    const response = Fetch(`/company/${id}`,'GET')

    const contentStyle = {
        marginLeft: '200px',
        backgroundColor: '#EFEEEE',
        padding: '20px',
    };

    if(click) return <GetEmployees/>

     function FetchAppointment(props:{appointment}){
        const id = useParams().id
        console.log("AQUI1",props.appointment)
        const response = Fetch(`/company/${id}/appointment/employees`,'POST',props.appointment)
        //const response = Appointment.addAppointmentWithManager(id,props.appointment,`/company/${id}/profile`)
        console.log("APPOINTMENT",response.response)
        SetClick(false)
        return <>
            {response.response?
                <> <p> Appointment added </p>
                <Navigate to = {`/company/${id}/profile`} />
               </>:
                <>Waiting ...</>
            }
        </>
    }

    return (
        <div  style = {contentStyle}>
            <div className="sidebar-left" >
                <Layout />
            </div>
            <div   style={{ margin:'20px', marginRight: '200px' }}>
            {
                !response.response?
                    <div className="loading">
                        <div className="spinner-border" role="status">
                            <span className="sr-only">Loading...</span>
                        </div>
                    </div>
                    :
                    <section style={{ backgroundColor: '#EFEEEE' }}>
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
                                                src={`data:image/jpeg;base64,${response.response.path[0]}`}
                                                alt="avatar"
                                                className="square"
                                                style={{ width: '200px', height: '200px', objectFit: 'cover' }}
                                                fluid />
                                        </MDBCardBody>
                                    </MDBCard>
                                    <MDBCard className="mb-4 mb-md-0">
                                        {roleCheckManager?
                                        <MDBCardBody>
                                            <p style={{ fontSize: '1.5rem', fontWeight: 'bold' }} ><strong> Managing your company </strong></p>
                                            <a href={`/company/${id}/managing/employees`} className="mb-1"  style={{ fontSize: '1.2rem' }}>
                                                <MDBCardText> Manage employees </MDBCardText>
                                            </a>
                                            <a href={`/company/${id}/managing/vacations`} className="mb-1"  style={{ fontSize: '1.2rem' }}>
                                                <MDBCardText> Manage company's vacations </MDBCardText>
                                            </a>
                                            <a href={`/company/${id}/services`} className="mb-1"  style={{ fontSize: '1.2rem' }}>
                                                <MDBCardText> Manage company's services </MDBCardText>
                                            </a>
                                            <a href={`/company/${id}/managing/unavailability`} className="mb-1"  style={{ fontSize: '1.2rem' }}>
                                                <MDBCardText> Add personal break </MDBCardText>
                                            </a>
                                            <a href={`/company/profits`} className="mb-1"  style={{ fontSize: '1.2rem' }}>
                                                <MDBCardText> Check your team profit </MDBCardText>
                                            </a>
                                            <Button style={{ borderColor: '#999999' ,backgroundColor: '#999999', color: 'white' }}
                                                    onClick={handleClick}
                                            >
                                                Add an appointment
                                            </Button>
                                        </MDBCardBody>:
                                            <MDBCardBody>
                                                <p style={{ fontSize: '1.5rem', fontWeight: 'bold' }} ><strong> Managing your company </strong></p>
                                                <a href={`/company/${id}/services`} className="mb-1"  style={{ fontSize: '1.2rem' }}>
                                                    <MDBCardText> Manage company's services </MDBCardText>
                                                </a>
                                                <a href={`/company/${id}/managing/unavailability`} className="mb-1"  style={{ fontSize: '1.2rem' }}>
                                                    <MDBCardText> Add personal break </MDBCardText>
                                                </a>
                                                <Button style={{ borderColor: '#999999' ,backgroundColor: '#999999', color: 'white' }}
                                                        onClick={handleClick}
                                                >
                                                    Add an appointment
                                                </Button>
                                            </MDBCardBody>
                                        }
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
                        <div >
                        <AdvancedCalendar />
                    </div>
                    </section>
            }
         </div>
            <LayoutRight/>
        </div>
    );
}


