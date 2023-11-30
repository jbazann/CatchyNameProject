"use strict";

import { connectSheetTracking } from './admin-dashboard-api.js'

export const textarea = document.getElementById('sheet-textarea');

connectSheetTracking();
