import * as React from "react";
import {useState} from "react";

import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";
import {Fetch} from "../../Utils/useFetch";
import {useParams} from "react-router-dom";


interface VacationDates{
    dateBegin : String,
    dateEnd: String
}


export function AddingVacations () {
    const today = new Date()
    const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(new Date());
    const [isSubmitting, setIsSubmitting] = useState(false);



    function handleSubmit(){
        setIsSubmitting(true)
    }

    function FetchAddVacation(){
        const vacationDate: VacationDates = {
            dateBegin : startDate.toISOString().substr(0, 10),
            dateEnd : endDate.toISOString().substr(0, 10)
        }

        console.log(vacationDate)

        const params = useParams()
        const cid = params.id
        const resp = Fetch(`/company/${cid}/vacation`,
            'POST',
            vacationDate
        )
        window.location.href = `/company/${cid}/managing/vacations`
        return(<></>);
    }


    return (
        <div style={{backgroundColor: '#0e4378'}}>
            {!isSubmitting?
                <section className="vh-100 gradient-custom">
                    <div className="container py-5 h-100">
                        <div className="row d-flex justify-content-center align-items-center h-100">
                            <div className="col-12 col-md-8 col-lg-6 col-xl-5">
                                <div className="card bg-white text-dark" style={{borderRadius: '1rem'}}>
                                    <div className="card-body p-5 text-center">
                                        <div className="mb-md-5 mt-md-4 pb-5">
                                            <h2 className="fw-bold mb-2 text-uppercase">Add vacations to your
                                                company</h2>
                                            <br/><br/>
                                            <div className="center">
                                                <div className="center">
                                                    <DatePicker selected={startDate}
                                                                minDate={today} // Define a data mínima como hoje
                                                                onChange={(date) => setStartDate(date)}
                                                    />
                                                </div>
                                                <label className="form-label"
                                                       htmlFor="typePasswordX">Vacation's start date</label>
                                            </div>
                                            <br/>
                                            <div className="center">
                                                <div className="center">
                                                    <DatePicker selected={endDate}
                                                                minDate={startDate} // Define a data mínima como hoje
                                                                onChange={(date) => setEndDate(date)}
                                                    />
                                                </div>
                                                <label className="form-label"
                                                       htmlFor="typePasswordX">Vacation's end date</label>
                                            </div>
                                            <br/>
                                            <button className="btn btn-outline-light btn-lg px-5" type="submit"
                                                    onClick={handleSubmit} style={{backgroundColor: 'black'}}>
                                                Create
                                            </button>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            :
            <FetchAddVacation/>
            }
        </div>
    );
}