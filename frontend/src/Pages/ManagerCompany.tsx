import * as React from 'react';
import {useParams} from "react-router-dom";
import {Fetch} from "../Utils/useFetch";
import {useState} from "react";
/*
import {
    ScheduleComponent, ResourcesDirective, ResourceDirective, ViewsDirective, ViewDirective,
    ResourceDetails, Inject, TimelineViews, Resize, DragAndDrop, TimelineMonth, Day
} from '@syncfusion/ej2-react-schedule';
*/
export function MyCompany() {
    const a = useParams()
    const res = Fetch(`/company/${a.id}`,'GET')
    const [submitVacation, setSubmitVacation] = useState(false)
    const [submitEmployees, setSubmitEmployees] = useState(false)

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

    return(
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
            <button className="btn btn-outline-light btn-lg px-5" type="submit"
                    onClick={handleAddVacation}>Add vacation
            </button>
            <button className="btn btn-outline-light btn-lg px-5" type="submit"
                    onClick={handleAddEmployees}>Add employees
            </button>
        </div>
    );
}



