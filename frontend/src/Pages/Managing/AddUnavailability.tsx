import React, {useState} from "react";
import {useParams} from "react-router-dom";
import {Fetch, SimpleFetch1} from "../../Utils/useFetch";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import {MDBInput} from "mdb-react-ui-kit";
import {Navigate} from "react-router";
import {Layout, LayoutRight} from "../Layout";
import {AccessDenied} from "../../Components/AcessDenied";
import {UnavailabilityServices} from "../../Service/UnavailabilityServices";

interface Unavailability {
    dateBegin: Date;
    dateEnd: Date;
    hourBegin: string;
    hourEnd: string;
}

export function AddingUnavailability() {
    const [isSubmitting, setIsSubmitting] = useState(false);
    const today = new Date();
    const [startDate, setStartDate] = useState(undefined);
    const [endDate, setEndDate] = useState(undefined);
    const [selectedTimeEnd, setSelectedTime] = useState(undefined);
    const [selectedTimeStart, setSelectedStart] = useState(undefined);
    const cid = useParams().id
    const handleTimeSelectStart = (time) => {
        const getTime = time.target.value
        setSelectedStart(getTime)
    };

    const handleTimeSelectEnd = (time) => {
        const getTime = time.target.value
        setSelectedTime(getTime)
    };

    const handleDateBeginSelectEnd = (date) => {
        const getDate = date.target.value
        setStartDate(getDate)
    };

    const handleDateEndSelectEnd = (date) => {
        const getDate = date.target.value
        setEndDate(getDate)
    };

    function handleSubmit() {
        setIsSubmitting(true);
    }

    function FetchAddUnavailability(){
        const unavail: Unavailability = {
            dateBegin: startDate,
            dateEnd: endDate,
            hourBegin: selectedTimeStart,
            hourEnd: selectedTimeEnd
        };

        UnavailabilityServices.createUnavailability(cid,unavail)
        setIsSubmitting(false);
        return <>
            <p>Can´t add unavailability</p>
            <Navigate to = {`/company/${cid}/managing/unavailability`}/>
        </>
    }

    const divElem = (
        <div
            style={{
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                minHeight: "100vh",
                backgroundColor: "#0e4378"
            }}
        >
            <div
                style={{
                    position: "relative",
                    width: "80%", // Ajuste o valor do width conforme necessário
                    backgroundColor: "#0e4378",
                    borderRadius: "1rem",
                    padding: "2rem",
                }}
            >
                {!isSubmitting ? (
                    <section className="gradient-custom">
                        <div className="row d-flex justify-content-center align-items-center">
                            <div className="col-12 col-md-8 col-lg-6 col-xl-5">
                                <div
                                    className="card bg-white text-dark"
                                    style={{
                                        borderRadius: "1rem",
                                        padding: "2rem",
                                        boxShadow: "0px 0px 10px rgba(0, 0, 0, 0.25)",
                                        margin: "1rem" // Ajuste o valor do margin conforme necessário
                                    }}
                                >
                                    <h2 className="fw-bold mb-2 text-uppercase">
                                        Unavailability long period:
                                    </h2>
                                    <br />
                                    <br />
                                    <div className="center">
                                        <div className="center">
                                            <label className="form-label" htmlFor="typePasswordX">Start date unavailable</label>
                                            <MDBInput
                                                wrapperClass="mb-4 mx-2 w-100"
                                                labelClass="text-white"
                                                label="Date"
                                                id="dateInput"
                                                type="date"
                                                size="lg"
                                                value={startDate}
                                                onChange={handleDateBeginSelectEnd}
                                                disabled={selectedTimeStart !== undefined || selectedTimeEnd !== undefined}
                                            />
                                        </div>
                                    </div>
                                    <br />
                                    <div className="center">
                                        <div className="center">
                                            <label className="form-label" htmlFor="typePasswordX">End date unavailable</label>
                                            <MDBInput
                                                wrapperClass="mb-4 mx-2 w-100"
                                                labelClass="text-white"
                                                label="Date"
                                                id="dateInput"
                                                type="date"
                                                size="lg"
                                                value={endDate}
                                                onChange={handleDateEndSelectEnd}
                                                disabled={selectedTimeStart !== undefined || selectedTimeEnd !== undefined}
                                            />
                                        </div>
                                    </div>
                                    <br />
                                    <p><strong> Add a period unavailability for today </strong></p>
                                    <br />
                                    <p>Begin Hour</p>
                                    <MDBInput
                                        wrapperClass="mb-4 mx-2 w-100"
                                        labelClass="text-white"
                                        label="Time"
                                        id="timeInput"
                                        type="time"
                                        size="lg"
                                        value={selectedTimeStart}
                                        onChange={handleTimeSelectStart}
                                        disabled={startDate !== undefined || endDate !== undefined}
                                    />
                                    <br />
                                    <p>End Hour</p>
                                    <MDBInput
                                        wrapperClass="mb-4 mx-2 w-100"
                                        labelClass="text-white"
                                        label="Time"
                                        id="timeInput"
                                        type="time"
                                        size="lg"
                                        value={selectedTimeEnd}
                                        onChange={handleTimeSelectEnd}
                                        disabled={startDate !== undefined || endDate !== undefined}
                                    />
                                    <button
                                        className="btn btn-outline-light btn-lg px-5"
                                        type="submit"
                                        onClick={handleSubmit}
                                        style={{ backgroundColor: "black" }}
                                        disabled={!selectedTimeStart && !selectedTimeEnd && !startDate && !endDate}
                                    >
                                        Create
                                    </button>
                                </div>
                            </div>
                        </div>
                    </section>
                ) : (
                    <FetchAddUnavailability />
                )}
            </div>
        </div>
    );

    return(
        (<div>
                <div className="sidebar-left">
                    <Layout />
                </div>
                <AccessDenied  company={cid} content={divElem} role={['MANAGER','EMPLOYEE']}/>
                <LayoutRight/>
            </div>
        )
    );
}