import React, {useEffect, useState} from 'react';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import '../Style/Schedule.css'
import {Fetch} from "../Utils/useFetch";
import {useParams} from "react-router-dom";
import {Navigate} from "react-router";
import {Button, Modal} from "react-bootstrap";
import {MyAppointments} from "./MyAppointments";
import {Company} from "./Company";

function TimePickerComponent (){

    const [startDate, setStartDate] = useState(new Date());
    const [click, setClick] = useState<string|undefined>(undefined);


    const params = useParams()
    const id = params.id


    const handleClick = (value) => {
        setClick(value)
    }

    function getWeekDay(date: Date): string {
        const daysOfWeek = ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'];
        const dayIndex = date.getDay();
        return daysOfWeek[dayIndex];
    }

    const myDate = startDate;
    const weekDay = getWeekDay(myDate);


    function handleDateChange(date){
        setStartDate(date)
    }


    const schedule = Fetch(`company/${id}/day/week-day?day=${weekDay}`, "GET")

    if(schedule.response){
        if(schedule.response.length === 0){
            return <>
                <div className="card-header bg-dark">
                    <div className="mx-0 mb-0 row justify-content-sm-center justify-content-start px-1">
                        <DatePicker
                            selected={startDate}
                            onChange={(date) => handleDateChange(date)} // Atualiza o estado com a data selecionada
                        />
                    </div>
                </div>
                <a>Closed for the day</a>
            </>
        }
    }
    console.log("schedule = ",schedule)

    return (
        <>
        {!click?
            <>
            {!schedule.response ?
                <div> ..loading.. </div>
                :
                <div className="container-fluid px-0 px-sm-4 mx-auto">
                    <div className="row justify-content-center mx-0">
                        <div className="col-lg-10">
                            <div className="card border-0">
                                <form autoComplete="off">
                                    <div className="card-header bg-dark">
                                        <div className="mx-0 mb-0 row justify-content-sm-center justify-content-start px-1">
                                            <DatePicker
                                                selected={startDate}
                                                onChange={(date) => handleDateChange(date)} // Atualiza o estado com a data selecionada
                                            />
                                        </div>
                                    </div>
                                    <div className="card-body p-3 p-sm-5">
                                        <div className="row text-center mx-0">
                                            {schedule.response.map((group, rowIndex) => (
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


function PopUpMessage(props:{id:string|undefined,date:Date,hour:string}) {
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
                    <Company/>
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

function FetchAvailableServices(props:{id:string,date:Date,hour:string}){
    console.log("")
    const response = Fetch(`/company/${props.id}/appointment/services/availability?hour_begin=${props.hour}&date=${props.date}`,'GET')

    console.log(response)
    //const response = Fetch(`/company/${props.id}/services`,'GET')


    function PopUpEmployees(){
          return <PopUpEmployee/>
    }

    console.log("Response pop",response.response)

    return  <Modal.Body>
        {response.response ?
            <>{response.response.length === 0 ?
                <> no available services </> :
                    <>
                        {
                            response.response.map((service) => (
                                <div className="row text-center mx-0" key={service.id}>
                                    <div className="col-md-2 col-4 my-1 px-2" key={service.id}>
                                        <div className="cell py-1"
                                             onClick={() => PopUpEmployees()}>{service.serviceName}</div>
                                    </div>
                                </div>
                            ))
                        }
                    </>
            }</>
            :
            <>No available Services :( </>
        }
    </Modal.Body>
}

function PopUpEmployee(){
    const [show, setShow] = useState(true);
    const [cancel, setCancel] = useState(false);
    const params = useParams()
    const id = params.id

    const handleClose = () => {
        setShow(false);
        window.location.href = `/company/${id}`;
    }

    const handleCancel = () => setCancel(true);

  return  <>
                <Company/>
                <Modal
                    show={show}
                    onHide={handleClose}
                    backdrop="static"
                    keyboard={false}
                >
                    <Modal.Header closeButton>
                        <Modal.Title>Available Employees for that service</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                       cenasss
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleClose}>
                            Close
                        </Button>
                        <Button variant="primary" onClick = {handleCancel}>Cancel</Button>
                    </Modal.Footer>
                </Modal>
    </>
}

export default TimePickerComponent;