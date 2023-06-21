import React, {useEffect, useState} from 'react';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import '../Style/Schedule.css'
import {Fetch, SimpleFetch} from "../Utils/useFetch";
import {useParams} from "react-router-dom";
import {Navigate} from "react-router";
import {Button, Modal} from "react-bootstrap";
import {MDBInput} from "mdb-react-ui-kit";
import { format } from 'date-fns';
import {LoggedInContextCookie} from "../Authentication/Authn";


function TimePickerComponent (){

    const [startDate, setStartDate] = useState(format(new Date(), 'yyyy-MM-dd'));
    const [click, setClick] = useState<string|undefined>(undefined);

    const params = useParams()
    const id = params.id

    const handleClick = (value) => {
        setClick(value)
    }

    function handleDateChange(date){
        setStartDate(date)
    }

    const schedule = Fetch(`company/${id}/day/week-day?day=${startDate}`, "GET").response

    if(schedule){
        if(schedule.length === 0){
            return (
                <>
                    <div className="card-header bg-dark">
                        <div className="mx-0 mb-0 row justify-content-sm-center justify-content-start px-1">
                            <DatePicker
                                selected={startDate}
                                onChange={(date) => handleDateChange(date)}
                            />
                        </div>
                    </div>
                    <a>Closed for the day</a>
                </>
            )
        }
    }

    console.log("schedule = ",schedule)

    return (
        <>
            {!click?
                <>
                    {!schedule?
                        <div> ..loading.. </div>
                        :
                        <div className="container-fluid px-0 px-sm-4 mx-auto">
                            <div className="row justify-content-center mx-0">
                                <div className="col-lg-10">
                                    <div className="card border-0">
                                        <form autoComplete="off">
                                            <div className="card-header bg-dark">
                                                <div className="mx-0 mb-0 row justify-content-sm-center justify-content-start px-1">
                                                    <MDBInput wrapperClass='mb-4 mx-5 w-100'
                                                              labelClass='text-white'
                                                              label=''
                                                              id='formControlLg'
                                                              type='date'
                                                              size="lg"
                                                              value={startDate}
                                                              required={true}
                                                              onChange={(e) => setStartDate(e.target.value)}
                                                    />
                                                </div>
                                            </div>
                                            <div className="card-body p-3 p-sm-5">
                                                <div className="row text-center mx-0">
                                                    {schedule.map((group, rowIndex) => (
                                                        <div className="row text-center mx-0" key={rowIndex}>
                                                            <div className="col-md-2 col-4 my-1 px-2" key={rowIndex}>
                                                                <div className="cell py-1"
                                                                     onClick={() => handleClick(group)}>{group}</div>
                                                            </div>
                                                        </div>
                                                    ))}
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    }
                </>
                :
                <PopUpMessage id={id} date={startDate} hour={click}/>
            }
        </>
    )
}


function PopUpMessage(props:{id:string|undefined,date:string,hour:string}) {
    console.log("props dat",props.date)
    const [show, setShow] = useState(true);

    const params = useParams()
    const id = params.id

    const handleClose = () => {
        setShow(false);
        window.location.href = `/company/${props.id}`;
    }


    if(props.id == undefined) return <Navigate to = {`/company/${id}`} replace={true}></Navigate>

    return (
        <>
            <Modal
                show={show}
                onHide={handleClose}
                backdrop="static"
                keyboard={false}
            >
                <Modal.Header closeButton>
                    <Modal.Title>Available Services</Modal.Title>
                </Modal.Header>
                <FetchAvailableServices id={props.id} date={props.date} hour={props.hour}/>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

function FetchAvailableServices(props:{id:string,date:string,hour:string}){

    console.log("fetch available services ", props.date)
    const response = Fetch(`/company/${props.id}/appointment/services/availability?hour_begin=${props.hour}&date=${props.date}`,'GET')
    const [employees, setEmployees] = useState({employees: undefined,
        serviceId: undefined
    })
    console.log(response)

    function PopUpEmployees(serviceId, employees){
        setEmployees({
            employees: employees,
            serviceId: serviceId
        })
    }

    console.log("Response pop",response.response)

    return <Modal.Body>
        {!employees.serviceId?
            <>
                {response.response ?
                    <>
                        {response.response.length === 0 ?
                            <> no available services </>
                            :
                            <>
                                {response.response.map((service) => (
                                    <div className="row text-center mx-0" key={service.first.id}>
                                        <div className="col-md-2 col-4 my-1 px-2" key={service.first.id}>
                                            <div className="cell py-1"
                                                 onClick={() => PopUpEmployees(service.first.id, service.second)}>{service.first.serviceName}
                                            </div>
                                        </div>
                                    </div>
                                ))
                                }
                            </>
                        }
                    </>
                    :
                    <>No available Services for this date </>
                }
            </>
            :
            <>
                <PopUpEmployee  appHour={props.hour} employees={employees} startDate={props.date}/>
            </>
        }
    </Modal.Body>
}

function PopUpEmployee(props: {employees, startDate, appHour}){
    const [show, setShow] = useState(true);
    const params = useParams()
    const id = params.id
    const [dados, setDados] = useState({});
    const [redirecionar, setRedirecionar] = useState(false);

    const handleCancel = () => {
        setShow(false);
        window.location.href = `/company/${id}`;
    }
    const token = React.useContext(LoggedInContextCookie).loggedInState.token;

    function handleClick(value){


        const obj = {
            appHour: props.appHour,
            appDate: props.startDate,
            service: props.employees.serviceId,
            user: value
        }

        SimpleFetch(`/api/company/${id}/appointment`,obj,'POST')
        setRedirecionar(true);
        fetch(`/api/company/${id}/appointment`, {
            method: 'POST',
            body: JSON.stringify(obj),
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => response.json())
            .then(data => {
                setDados(data);
                setRedirecionar(true);
            })
            .catch(error => {
                console.error('Ocorreu um erro:', error);
        });
    }

    useEffect(() => {
        if (redirecionar) {
            setRedirecionar(false);
        }
    }, [redirecionar]);

    if (redirecionar) {
        alert("Your appointment has been scheduled")
        return <Navigate to = '/' />;
    }

    return(
        <>
            <Modal
                show={show}
                onHide={handleCancel}
                backdrop="static"
                keyboard={false}
            >
                <Modal.Header closeButton>
                    <Modal.Title>Available Employees for that service</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {props.employees.employees.map((employee) => (
                        <div className="row text-center mx-0" key={employee.id}>
                            <div className="col-md-2 col-4 my-1 px-2" key={employee.id}>
                                <div className="cell py-1"
                                     onClick={() => handleClick(employee.id)}>{employee.name}
                                </div>
                            </div>
                        </div>
                    ))
                    }
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" onClick={handleCancel}>Cancel</Button>
                </Modal.Footer>
            </Modal>
        </>
    )
}

export default TimePickerComponent;