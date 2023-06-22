import * as React from "react";
import {useState} from "react";
import "../Style/CreatingCompany.css"
import "../Style/CreatingSchedule.css"
import 'rc-picker/assets/index.css';
import {Fetch} from "../Utils/useFetch";
import {useParams} from "react-router-dom";
import {convertMinutesToHHMMSS} from "../Utils/formater";


export function CreatingSchedule() {
    const daysOfWeek = ['MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN'];

    const [create, setCreate] = useState<boolean>(false)

    const [schedule, setSchedule] = useState(
        daysOfWeek.map(day => ({
            weekDays: day,
            selected: false,
            beginHour: '',
            endHour: ''
        }))
    );

    const [isOpen, setIsOpen] = useState(false);
    const [selectedValue, setSelectedValue] = useState(null);

    const handleDropdownToggle = () => {
        setIsOpen(!isOpen);
    };

    const handleOptionClick = (value) => {
        setSelectedValue(
            value
          //  convertMinutesToHHMMSS(value)
        );
        setIsOpen(false);
    };

    console.log("selected value = ", selectedValue)

    const renderOptions = () => {
        const options = [];
        for (let i = 1; i <= 200; i++) {
            options.push(
                <div
                    key={i}
                    className="dropdown-option"
                    onClick={() => handleOptionClick(i)}
                >
                    {i} minutes
                </div>
            );
        }
        return options;
    };

    const handleTimeChange = (index, field, value) => {
        setSchedule(prevSchedule => {
            const updatedSchedule = [...prevSchedule];
            updatedSchedule[index][field] = value;

            if (field === "endHour" && value < updatedSchedule[index].beginHour) {
                updatedSchedule[index].beginHour = value; // Atualiza a hora de início para ser igual à hora de término
            }

            if (field === "beginHour" && value > updatedSchedule[index].endHour) {
                updatedSchedule[index].endHour = value; // Atualiza a hora de início para ser igual à hora de término
            }

            return updatedSchedule;
        });
    };

    const handleSameTimeButtonClick = () => {
        const selectedDays = schedule.filter(day => day.selected);

        if (selectedDays.length > 0) {
            const beginHour = selectedDays[0].beginHour;
            const endHour = selectedDays[0].endHour;

            setSchedule(prevSchedule =>
                prevSchedule.map(day => {
                    if (day.selected) {
                        return { ...day, beginHour, endHour };
                    }
                    return day;
                })
            );
        }
    };

    const handleCheckboxChange = (index, checked) => {
        setSchedule(prevSchedule => {
            const updatedSchedule = [...prevSchedule];
            updatedSchedule[index].selected = checked;
            return updatedSchedule;
        });
    };

    function FetchCreateDays(){
        const params = useParams()
        const id = params.id
        const body =  schedule
            .filter(day => day.beginHour !== '' && day.endHour !== '')
            .map(day => ({
                weekDays: day.weekDays,
                beginHour: day.beginHour,
                endHour: day.endHour
            }))
        const resp = Fetch(`company/${id}/day/all?duration=${convertMinutesToHHMMSS(selectedValue)}`,
            'POST',
            body).response
        if(!resp) return(<p>...loading...</p>);
        if(resp.status) {
            setCreate(false)
            window.location.href = `/company/${id}/employees`
            return(<></>);
        }
        if(resp){
            console.log(resp)
            const companyId = resp.id
            console.log("company = ", companyId)
            window.location.href = `/company/${companyId}/schedule`
            return(
                <></>
            )
        }
    }

    function handleCreate(){
        setCreate(true)
        console.log(
            schedule
                .filter(day => day.beginHour !== '' && day.endHour !== '')
                .map(day => ({
                    week_days: day.weekDays,
                    begin_hour: day.beginHour,
                    end_hour: day.endHour
                }))
        );
    }




    return (
        <div>
            {!create ? (
                <section className="vh-100 gradient-custom">
                    <div className="container py-5 h-100">
                        <div className="row d-flex justify-content-center align-items-center h-100">
                            <div className="col-12 col-md-8 col-lg-6 col-xl-5">
                                <div className="card bg-dark text-white" style={{ borderRadius: '1rem' }}>
                                    <div className="card-body p-5 text-center">
                                        <div className="mb-md-5 mt-md-4 pb-5">
                                            <h2 className="bold">Define your company's schedule!</h2>
                                            <br /><br />
                                            <div className="table-container">
                                                <table className="schedule-table">
                                                    <thead>
                                                    <tr>
                                                        <th>Selected</th>
                                                        <th>Week Day</th>
                                                        <th>Begin Hour</th>
                                                        <th>End Hour</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    {schedule.map((day, index) => (
                                                        <tr key={index}>
                                                            <td>
                                                                <input
                                                                    type="checkbox"
                                                                    checked={day.selected}
                                                                    onChange={(e) => handleCheckboxChange(index, e.target.checked)}
                                                                />
                                                            </td>
                                                            <td>{day.weekDays}</td>
                                                            <td>
                                                                <input
                                                                    type="time"
                                                                    value={day.beginHour}
                                                                    onChange={(e) => handleTimeChange(index, "beginHour", e.target.value)}
                                                                    step="600" // Adicione essa linha
                                                                />
                                                            </td>
                                                            <td>
                                                                <input
                                                                    type="time"
                                                                    value={day.endHour}
                                                                    min={day.beginHour}
                                                                    onChange={(e) => handleTimeChange(index, "endHour", e.target.value)}
                                                                    step="600" // Adicione essa linha
                                                                />
                                                            </td>
                                                        </tr>
                                                    ))}
                                                    </tbody>
                                                </table>
                                                <button onClick={handleSameTimeButtonClick}>Same Time</button>
                                            </div>
                                            <br />
                                            <div className="container">
                                                <div className="row">
                                                    <div className="col-lg-12">
                                                        <div className="btn-group">
                                                            <div className="dropdown">
                                                                <label>
                                                                    Duration between each block (in minutes):
                                                                </label>
                                                                <br />
                                                                <button className="dropdown-toggle" onClick={handleDropdownToggle}>
                                                                    {selectedValue?
                                                                        `${selectedValue} minutes`:
                                                                        "Select a number"
                                                                    }
                                                                </button>
                                                                {isOpen && (
                                                                    <div className="dropdown-options-container">
                                                                        <div className="dropdown-options">{renderOptions()}</div>
                                                                    </div>
                                                                )}
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <br /><br />

                                            <button className="btn btn-outline-light btn-lg px-5" type="submit" onClick={handleCreate}>
                                                Next
                                            </button>
                                            <br />
                                            <br />
                                            <br />
                                            <button className="btn btn-outline-light btn-lg px-5" type="submit" onClick={() => {}}>
                                                Configure Later
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            ) : (
                <FetchCreateDays />
            )}
        </div>
    );
}