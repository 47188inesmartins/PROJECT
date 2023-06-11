import * as React from 'react'
import { createRoot } from 'react-dom/client'
import ScheduleItApp from "./Utils/App";

const root = createRoot(document.getElementById("the-div"))

root.render(
    <ScheduleItApp />
)

