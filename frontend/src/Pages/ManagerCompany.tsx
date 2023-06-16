import * as React from 'react';
import {useParams} from "react-router-dom";
import {Fetch} from "../Utils/useFetch";
import {useState} from "react";
import MyCalendar from "./AdvancedCalendar";
import "../Style/Test.css";
import _default from "chart.js/dist/plugins/plugin.tooltip";
import backgroundColor = _default.defaults.backgroundColor;

export function MyCompany() {
    const a = useParams()
    const res = Fetch(`/company/${a.id}`,'GET')
    const [submitVacation, setSubmitVacation] = useState(false)
    const [submitEmployees, setSubmitEmployees] = useState(false)
    const [submitServices, setSubmitServices] = useState(false)
    const [submitSchedule, setSubmitSchedule] = useState(false)

    const handleAddVacation = () => {
        setSubmitVacation(true)
    }

    const handleAddEmployees = () => {
        setSubmitEmployees(true)
    }


    function FetchAddEmployee(){
        const params = useParams()
        const id = params.id
        window.location.href = `/company/${id}/employees`
        return(
            <></>
        )
    }

    function FetchAddVacation(){
        const params = useParams()
        const id = params.id
        window.location.href = `/company/${id}/vacation`
        return(
            <></>
        )
    }

    function FetchAddServices(){
        const params = useParams()
        const id = params.id
        window.location.href = `/company/${id}/services`
        return(
            <></>
        )
    }

    function handleChangeSchedule(){
       setSubmitSchedule(true)
    }

    function handleAddServices(){
       setSubmitServices(true)
    }

    function FetchChangeSchedule(){
        const params = useParams()
        const id = params.id
        window.location.href = `/company/${id}/schedule`
        return(
            <></>
        )
    }

    return(
        <div>
            {!submitSchedule?
                <div>
                    {!submitServices?
                        <div>
                            {!submitEmployees?
                                <div>
                                    {!submitVacation?
                                        <div>
                                            {!res.response?
                                                <div className="loading">
                                                    <div className="spinner-border" role="status">
                                                        <span className="sr-only">Loading...</span>
                                                    </div>
                                                </div>
                                                :
                                                <>
                                                    <h1>{res.response.name}</h1>
                                                </>
                                            }
                                        </div>
                                        :
                                        <FetchAddVacation/>
                                    }
                                </div>
                                :
                                <FetchAddEmployee/>
                            }
                        </div>
                        :
                        <FetchAddServices/>
                    }
                </div>
            :
            <FetchChangeSchedule/>
            }

            <MyCalendar/>
            <div style={{ display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
                <button className="btn btn-outline-light btn-lg px-5" type="submit" onClick={handleAddVacation} style={{ backgroundColor: 'black', marginRight: '10px' }}>Add vacation</button>
                <button className="btn btn-outline-light btn-lg px-5" type="submit" onClick={handleAddEmployees} style={{ backgroundColor: 'black', marginRight: '10px' }}>Add employees</button>
                <button className="btn btn-outline-light btn-lg px-5" type="submit" onClick={handleChangeSchedule} style={{ backgroundColor: 'black', marginRight: '10px' }}>Change schedule</button>
                <button className="btn btn-outline-light btn-lg px-5" type="submit" onClick={handleAddServices} style={{ backgroundColor: 'black', marginRight: '10px' }}>Add services</button>
            </div>
       </div>
    );
}



