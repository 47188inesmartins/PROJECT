import React, { useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { useParams } from "react-router-dom";
import {Layout, LayoutRight} from "../Layout";
import {AccessDenied} from "../../Components/AccessDenied";
import {VacationService} from "../../Service/VacationServices";

interface VacationDates {
    dateBegin: string;
    dateEnd: string;
}

export function AddingVacations() {
    const today = new Date();
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(new Date());
    const [isSubmitting, setIsSubmitting] = useState(false);
    const params = useParams();
    const cid = params.id;

    function handleSubmit() {
        setIsSubmitting(true);
    }

    function FetchAddVacation() {
        const vacationDate: VacationDates = {
            dateBegin: startDate.toISOString().substr(0, 10),
            dateEnd: endDate.toISOString().substr(0, 10)
        };
        console.log(vacationDate);
        VacationService.addVacation(cid,vacationDate)
        //setIsSubmitting(false);

        //const resp = Fetch(`/company/${cid}/vacation`, "POST", vacationDate);
        //console.log("response",resp.response)
        setIsSubmitting(false);
        //window.location.href = `/company/${cid}/managing/vacations`;
        return null
    }

    const divElem = <div className="w-100 d-flex justify-content-center"
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
                <div className="row d-flex justify-content-center align-items-center">
                    <div className="col-12 col-md-12 col-lg-10 col-xl-8">
                        <div
                            className="card bg-white text-dark"
                            style={{
                                borderRadius: "1rem",
                                padding: "2rem",
                                boxShadow: "0px 0px 10px rgba(0, 0, 0, 0.25)",
                                margin: "1rem",
                                width: '100%'
                            }}
                        >
                            <h2 className="fw-bold mb-2 text-uppercase">Add vacations to your company</h2>
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
            </section>
        ) : (
            <FetchAddVacation />
        )}
    </div>

    return (
        <div>
            <div className="sidebar-left">
                <Layout />
            </div>
            <div className="d-flex justify-content-center"> {/* Adicione esta div para centralizar horizontalmente */}
                <AccessDenied company={cid} content={divElem} role={['MANAGER']} />
            </div>
            <LayoutRight />
        </div>
    );


}
