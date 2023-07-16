import React, {useState} from "react";
import {useParams} from "react-router-dom";
import {Fetch} from "../../Utils/useFetch";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import {MDBInput} from "mdb-react-ui-kit";

interface Unavailability {
    dateBegin: Date;
    dateEnd: Date;
    hourBegin: string;
    hourEnd: string;
}

export function AddingUnavailability() {
    const [isSubmitting, setIsSubmitting] = useState(false);
    const today = new Date();
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(new Date());
    const [selectedTimeEnd, setSelectedTime] = useState("");
    const [selectedTimeStart, setSelectedStart] = useState("");

    const handleTimeSelectStart = (time) => {
        const getTime = time.target.value
        setSelectedStart(getTime)
    };

    const handleTimeSelectEnd = (time) => {
        const getTime = time.target.value
        setSelectedTime(getTime)
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
        const cid = useParams().id

        const response = Fetch(`/unavailability/company/${cid}`,'POST',unavail)
        window.location.href = `/company/${cid}/profile`;
        return <></>
    }

    return(
        <div
            style={{
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                minHeight: "100vh",
                backgroundColor: "#0e4378"
            }}
        >
            {!isSubmitting ? (
                <section className="gradient-custom">
                    <div className="container">
                        <div className="row d-flex justify-content-center align-items-center">
                            <div className="col-12 col-md-8 col-lg-6 col-xl-5">
                                <div className="card bg-white text-dark" style={{ borderRadius: "1rem" }}>
                                    <div className="card-body p-5 text-center">
                                        <div className="mb-md-5 mt-md-4 pb-5">
                                            <h2 className="fw-bold mb-2 text-uppercase">Unavailability long period:</h2>
                                            <br />
                                            <br />
                                            <div className="center">
                                                <div className="center">
                                                    <DatePicker
                                                        selected={startDate}
                                                        minDate={today}
                                                        onChange={(date) => setStartDate(date)}
                                                    />
                                                </div>
                                                <label className="form-label" htmlFor="typePasswordX">Vacation's start date</label>
                                            </div>
                                            <br />
                                            <div className="center">
                                                <div className="center">
                                                    <DatePicker
                                                        selected={endDate}
                                                        minDate={startDate}
                                                        onChange={(date) => setEndDate(date)}
                                                    />
                                                </div>
                                                <label className="form-label" htmlFor="typePasswordX">Vacation's end date</label>
                                            </div>
                                            <br />
                                            <p><strong> Add a period unavailability for today </strong></p>
                                            <MDBInput
                                                wrapperClass="mb-4 mx-5 w-100"
                                                labelClass="text-white"
                                                label="Time"
                                                id="timeInput"
                                                type="time"
                                                size="lg"
                                                value={selectedTimeStart}
                                                onChange={handleTimeSelectStart}
                                            />
                                            <br />
                                            <MDBInput
                                                wrapperClass="mb-4 mx-5 w-100"
                                                labelClass="text-white"
                                                label="Time"
                                                id="timeInput"
                                                type="time"
                                                size="lg"
                                                value={selectedTimeEnd}
                                                onChange={handleTimeSelectEnd}
                                            />
                                            <button
                                                className="btn btn-outline-light btn-lg px-5"
                                                type="submit"
                                                onClick={handleSubmit}
                                                style={{ backgroundColor: "black" }}
                                            >
                                                Create
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            ) : (
                <FetchAddUnavailability />
            )}
        </div>
    );

}