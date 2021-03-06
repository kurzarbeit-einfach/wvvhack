var steps = [
    {
        "id": "hallo",
        "render": () => {
            textAndInteraction(
                "Mit den Grundlagen kennst du dich jetzt aus. Lass uns anfangen, das Formular zur Einreichung bei der Bundesagentur für Arbeit gemeinsam auszufüllen. Dazu stelle ich dir eine Reihe von Fragen. Deine Antworten nutze ich, um die Formularfelder für dich zu füllen. Danach kannst du es herunterladen, prüfen, ggfs. anpassen und dann an die Agentur übertragen. Los geht's!",
                () => createConfirmButton("anzahl_beschaeftigte", "Ich bin bereit")
            );
        }
    },
    {
        "id": "anzahl_beschaeftigte",
        "render": () => {
            textAndInteraction(
                "Wieviele Beschäftigte hat dein Unternehmen?",
                () => {
                    createIntegerInput(state.answers["anzahl_beschaeftigte"] || 1, 0, null, 1, 1, false).then( (result) => { 
                        setCurrentAnswer(result.value);

                        if (result.value == 1) {
                            createTextMessage("Ich habe zur Zeit _einen Beschäftigten_.","",true);
                        } else {
                            createTextMessage("Ich habe zur Zeit _"+result.value+" Beschäftigte_.","",true);
                        }
                        
                        if (result.value <= 0) {
                            renderStep("keine_beschaeftigte");
                        } else if(result.value <= 50) {
                            renderStep("arbeitzeitreduzierung_fuer");
                        } else {
                            renderStep("anzahl_beschaeftigte_hoch"); 
                        }
                    });
                },
                "Einschließlich geringfügig Beschäftigte (Mini-Job oder &quot;450 EUR&quot;-Job) und Leiharbeiter. Azubis zählen hier nicht mit."
            );
        }
    },
    {
        "id": "keine_beschaeftigte",
        "render": () => {
            exitTextAndModifyQuestion(
                "Nur Unternehmen mit mindestens einem Beschäftigten können Kurzarbeitergeld beantragen.",
                "anzahl_beschaeftigte",
                "Es gibt Möglichkeiten für Selbstständige. Schau' doch mal nach unter [taxy.io](https://www.taxy.io/corona-hilfe-fuer-unternehmen)^ oder [wir-bleiben-liqui.de](https://wir-bleiben-liqui.de/)^."
            );
        }
    },
    {
        "id": "anzahl_beschaeftigte_hoch",
        "render": () => {
            textAndInteraction(
                "Das tut mir leid; ich bin leider nicht gut für Unternehmen mit mehr als 50 Beschäftigten vorbereitet. Bitte wende dich an deine Bundesagentur für Arbeit unter 0800 4555520 (gebührenfrei) für eine individuelle Beratung. Möchtest du dennoch fortfahren?",
                () => createYesNoButtons("arbeitzeitreduzierung_fuer", "ende_ohne_pdf", "Weiter", "Ende")
            );
        }
    },
    {
        "id": "arbeitzeitreduzierung_fuer",
        "render": () => {
            textAndInteraction(
                "Betrifft die Kurzarbeit nur eine Abteilung oder das gesamte Unternehmen?",
                () => {
                    botui.action.button({
                        action: [
                            {
                                icon: 'sitemap',
                                text: 'Abteilung',
                                value: 'Abteilung'
                            },
                            {
                                icon: 'building',
                                text: 'Gesamtes Unternehmen',
                                value: 'Gesamtes Unternehmen'
                            }
                        ]
                    }).then( (res) => {
                        setCurrentAnswer(res.value);
                        if (res.value === 'Abteilung') {
                            renderStep("arbeitzeitreduzierung_fuer_abteilung");
                        } else {
                            renderStep("anzahl_beschaeftigte_in_kurzarbeit");
                        }
                    });
                }
            );
        }
    },
    {
        "id": "arbeitzeitreduzierung_fuer_abteilung",
        "render": () => {
            textAndInteraction(
                "Für welche Abteilung gilt die Kurzarbeit?",
                () => {
                    createTextInput(state.answers["arbeitzeitreduzierung_fuer_abteilung"] || null, true).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("anzahl_beschaeftigte_in_abteilung");
                    });
                },
                "Kriterien für eine Abteilung: Eigene fachlich-technische Leitung, Eigener arbeitstechnischer Zweck/Hilfszweck, Geschlossene Arbeitsgruppe, Eigene Arbeitsmittel, Räumliche Trennung vom übrigen Betrieb"
            );
        }
    },
    {
        "id": "anzahl_beschaeftigte_in_abteilung",
        "render": () => {
            textAndInteraction(
                "Wieviele Beschäftigte hat diese Abteilung?",
                () => {
                    createIntegerInput(state.answers["anzahl_beschaeftigte_in_abteilung"] || 1, 0, state.answers["anzahl_beschaeftigte"], 1, 1, false).then( (result) => { 
                        setCurrentAnswer(result.value);

                        if (result.value == 1) {
                            createTextMessage("Die betroffene Abteilung hat _einen Beschäftigten_.","",true);
                        } else {
                            createTextMessage("Die betroffene Abteilung hat _"+result.value+" Beschäftigte_.","",true);
                        }
                        
                        if (result.value <= 0) {
                            renderStep("keine_beschaeftigte_in_abteilung");
                        } else {
                            renderStep("anzahl_beschaeftigte_in_kurzarbeit"); 
                        }
                    });
                },
                "Einschließlich geringfügig Beschäftigte (Mini-Job oder &quot;450 EUR&quot;-Job) und Leiharbeiter. Azubis zählen hier nicht mit."
            );
        }
    },    
    {
        "id": "keine_beschaeftigte_in_abteilung",
        "render": () => {
            exitTextAndModifyQuestion(
                "Für Abteilungen ohne Beschäftigte kann leider kein Geld für Kurzarbeit beantragt werden.",
                "anzahl_beschaeftigte_in_abteilung",
                "Es gibt andere Möglichkeiten. Schau' doch mal nach unter [taxy.io](https://www.taxy.io/corona-hilfe-fuer-unternehmen)^ oder [wir-bleiben-liqui.de](https://wir-bleiben-liqui.de/)^."
            );
        }
    },
    {
        "id": "anzahl_beschaeftigte_in_kurzarbeit",
        "render": () => {
            const betrifftAbteilung = state.answers["arbeitzeitreduzierung_fuer"] === "Abteilung";

            textAndInteraction(
                betrifftAbteilung ? "Wieviele Beschäftigte sollen in der Abteilung voraussichtlich in Kurzarbeit gehen?" : "Wieviele Beschäftigte sollen voraussichtlich in Kurzarbeit gehen?",
                () => {                    
                    createIntegerInput(state.answers["anzahl_beschaeftigte_in_kurzarbeit"] || 1, 0, null, 1, true, false).then( (result) => { 
                        setCurrentAnswer(result.value);

                        if(result.value == 1)
                            createTextMessage(`Es soll _ein Beschäftigter_ in Kurzarbeit gehen.`,"",true);
                        else
                            createTextMessage(`Es sollen _${result.value} Beschäftigte_ in Kurzarbeit gehen.`,"",true);

                        const anzahlMitarbeiterGesamtbetriebOderAbteilung =
                            betrifftAbteilung
                                ? state.answers["anzahl_beschaeftigte_in_abteilung"]
                                : state.answers["anzahl_beschaeftigte"];

                        if (result.value <= 0) {
                            renderStep("keine_beschaeftigte_in_kurzarbeit");
                        } else if((result.value / anzahlMitarbeiterGesamtbetriebOderAbteilung * 100) < 10) {
                            if (betrifftAbteilung) {
                                renderStep("anzahl_beschaeftigte_in_kurzarbeit_in_abteilung_kleiner_10_prozent");
                            } else {                                
                                renderStep("anzahl_beschaeftigte_in_kurzarbeit_kleiner_10_prozent");
                            }                            
                        } else {
                            renderStep("anzahl_wochenstunden_der_beschaeftigten_regulaer");
                        }
                    });
                },
		        "<ul><li>Fast alle betroffenen sozialversicherungspflichtig beschäftigten Mitarbeiter können Kurzarbeitergeld erhalten &ndash; auch zeitlich befristet angestellte Mitarbeiter und Auszubildende.</li><li>Ausgenommen sind z.B. geringfügig Beschäftigte (Mini-Job oder &quot;450 EUR&quot;-Job) und gekündigte Mitarbeiter.</li></ul>"
            );
        }
    },
    {
        "id": "keine_beschaeftigte_in_kurzarbeit",
        "render": () => {
            exitTextAndModifyQuestion(
                "Du möchtest niemanden kurzarbeiten lassen.",
                "anzahl_beschaeftigte_in_kurzarbeit"
            );
        }
    },
    {
        "id": "anzahl_beschaeftigte_in_kurzarbeit_kleiner_10_prozent",
        "render": () => {
            exitTextAndModifyQuestion(
                `Du möchtest nur ${state.answers["anzahl_beschaeftigte_in_kurzarbeit"]} von ${state.answers["anzahl_beschaeftigte"]} Beschäftigten kurzarbeiten lassen. Um Kurzarbeitergeld zu erhalten musst du aber mindestens 10% deiner Beschäftigten kurzarbeiten lassen.`,
                "anzahl_beschaeftigte_in_kurzarbeit"
            );
        }
    },
    {
        "id": "anzahl_beschaeftigte_in_kurzarbeit_in_abteilung_kleiner_10_prozent",
        "render": () => {
            exitTextAndModifyQuestion(
                `Du möchtest nur ${state.answers["anzahl_beschaeftigte_in_kurzarbeit"]} von ${state.answers["anzahl_beschaeftigte_in_abteilung"]} Beschäftigten in dieser Abteilung kurzarbeiten lassen. Um Unterstützung zu erhalten musst du mindestens 10% deiner Beschäftigten kurzarbeiten lassen.`,
                "anzahl_beschaeftigte_in_kurzarbeit"
            );
        }
    },
    {
        "id": "anzahl_wochenstunden_der_beschaeftigten_regulaer",
        "render": () => {
            textAndInteraction(
                "Wieviele Stunden arbeiten deine Beschäftigten normalerweise pro Woche?",
                () => {                    
                    createIntegerInput(state.answers["anzahl_wochenstunden_der_beschaeftigten_regulaer"] || 40, 1, null, 0.5, true, false).then( (result) => { 
                        setCurrentAnswer(result.value);

                        if(result.value == 1)
                            createTextMessage(`Sie arbeiten normalerweise _eine Stunde_ in der Woche.`,"",true);
                        else
                            createTextMessage(`Sie arbeiten normalerweise _${result.value} Stunden_ in der Woche.`,"",true);
                            
                        renderStep("anzahl_wochenstunden_der_beschaeftigten_in_kurzarbeit");
                    });
                }
            );
        }
    },
    {
        "id": "anzahl_wochenstunden_der_beschaeftigten_in_kurzarbeit",
        "render": () => {
            textAndInteraction(
                "Wieviele Stunden sollen diese Beschäftigten voraussichtlich während der Kurzarbeit pro Woche arbeiten?",
                () => {                    
                    createIntegerInput(state.answers["anzahl_wochenstunden_der_beschaeftigten_in_kurzarbeit"] || 20, 0, state.answers["anzahl_wochenstunden_der_beschaeftigten_regulaer"], 1, true, false).then( (result) => { 
                        setCurrentAnswer(result.value);

                        if (result.value == 0) {
                            createTextMessage(`Sie sollen _gar nicht_ arbeiten.`,"",true);
                        } else if(result.value == 1) {
                            createTextMessage(`Sie sollen _eine Stunde_ in der Woche arbeiten.`,"",true);
                        } else {
                            createTextMessage(`Sie sollen _${result.value} Stunden_ in der Woche arbeiten.`,"",true);
                        }

                        renderStep("ist_verursacht_durch_unabwendbares_ereignis_oder_wirtschaftlich");
                    });
                },
                "<ul><li>Wenn die Beschäftigten voraussichtlich gar nicht mehr arbeiten sollen, so kannst du hier einfach 0 Stunden angeben.</li><li>Um Unterstützung zu erhalten müssen durch die Kurzarbeit mindestens 10% des Gehaltes der betroffenen Mitarbeiter wegfallen.</li></ul>"
            );
        }
    },
    {
        "id": "ist_verursacht_durch_unabwendbares_ereignis_oder_wirtschaftlich",
        "render": () => {
            textAndInteraction(
                "Worauf beruht der Arbeitsausfall?",
                () => {
                    botui.action.button({
                        action: [
                            {
                                icon: 'times',
                                text: "Andere Ursache",
                                value: false
                            },
                            {
                                icon: 'check',
                                text: "Wirtschaftliche Ursache",
                                value: true
                            },
                            {
                                icon: 'check',
                                text: "Unabwendbares Ereignis",
                                value: true
                            }
                        ]         
                    }).then( (res) => {
                        setCurrentAnswer(res.value);
                        if(res.value)
                            renderStep("sind_alle_massnahmen_zur_vermeidung_umgesetzt_worden");
                        else
                            renderStep("nicht_verursacht_durch_unabwendbares_ereignis_oder_wirtschaftlich");
                    });
                },
                "<ul><li>Eine wirtschaftliche Ursache ist z.B. ein aufgrund der Corona-Krise stornierter Großauftrag.</li><li>Ein unabwendbares Ereignis ist z.B. eine angeordnete Schließung zur Vorbeugung einer weiteren Ausbreitung der Corona-Pandemie.</li></ul>"
            );
        }
    },
    {
        "id": "nicht_verursacht_durch_unabwendbares_ereignis_oder_wirtschaftlich",
        "render": () => {
            exitTextAndModifyQuestion(
                `Die Voraussetzung für die Gewährung von Kurzarbeitergeld ist, dass der Arbeitsausfall auf einem unabwendbaren Ereignis basiert oder wirtschaftliche Ursachen hat.`,
                "ist_verursacht_durch_unabwendbares_ereignis_oder_wirtschaftlich"
            );
        }
    },
    {
        "id": "sind_alle_massnahmen_zur_vermeidung_umgesetzt_worden",
        "render": () => {
            textAndInteraction(
                "Sind alle internen Maßnahmen zur Vermeidung von Kurzarbeit umgesetzt worden?",
                () => createYesNoButtons("branche_freitext", "massnahmen_zur_vermeidung_sind_nicht_umgesetzt_worden", "Maßnahmen umgesetzt", "Nicht umgesetzt"),
                "Beispiele: Resturlaub aufbrauchen, Überstunden abbauen, Mitarbeiter in andere Abteilungen umsetzen"
            );
        }
    },
    {
        "id": "massnahmen_zur_vermeidung_sind_nicht_umgesetzt_worden",
        "render": () => {
            exitTextAndModifyQuestion(
                "Die Umsetzung aller Maßnahmen zur Vermeidung von Kurzarbeit sind Voraussetzung für die Einführung von Kurzarbeit.",
                "sind_alle_massnahmen_zur_vermeidung_umgesetzt_worden"
            );
        }
    },
    {
        "id": "branche_freitext",
        "render": () => {
            textAndInteraction(
                "Welcher Branche gehört dein Unternehmen an?",
                () => {
                    createTextInput(state.answers["branche_freitext"] || null, true).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("sektion_gruende");
                    });
                }
            );
        }
    },
    {
        "id": "sektion_gruende",
        "render": () => {
            textAndInteraction(
                "Vielen Dank für diese ersten Informationen. Nun müssen wir der Bundesagentur für Arbeit erklären, wie es zu deinem Arbeitsausfall gekommen ist. Hierzu stelle ich dir nun ein paar Fragen zu deinem Unternehmen.",
                () => createConfirmButton("ursachen_fuer_arbeitsausfall", "OK, verstanden")
            );
        }
    },
    {
        "id": "ursachen_fuer_arbeitsausfall",
        "render": () => {
            textAndInteraction(
                "Was sind die Ursachen für den Arbeitsausfall?",
                () => {
                    createTextareaInput(state.answers["ursachen_fuer_arbeitsausfall"] || null, "Bitte eingeben...", 5).then( (result) => { 
                        setCurrentAnswer(result.value);
                        if(result.value.length < 20) {
                            renderStep("zu_kurze_ursachen_fuer_arbeitsausfall");
                        } else {
                            renderStep("angaben_zu_produkten_dienstleistungen_auftraggebern_und_auftragnehmern");
                        }
                    });
                }
            );
        }
    },
    {
        "id": "zu_kurze_ursachen_fuer_arbeitsausfall",
        "render": () => {
            textAndInteraction(
                "Dein Text zu den Ursachen für den Arbeitsausfall ist sehr kurz. Du solltest hier die Ursachen genauer beschreiben.",
                () => createConfirmButton("ursachen_fuer_arbeitsausfall", "Bearbeiten")
            );
        }
    },
    {
        "id": "angaben_zu_produkten_dienstleistungen_auftraggebern_und_auftragnehmern",
        "render": () => {
            textAndInteraction(
                "Welche Produkte und Dienstleistungen bietet dein Unternehmen an? Wer sind deine Hauptauftraggeber bzw. -nehmer?",
                () => {
                    createTextareaInput(state.answers["angaben_zu_produkten_dienstleistungen_auftraggebern_und_auftragnehmern"] || null, "Bitte eingeben...", 5).then( (result) => { 
                        setCurrentAnswer(result.value);
                        if (result.value.length < 20) {
                            renderStep("zu_kurze_angaben_zu_produkten_dienstleistungen_auftraggebern_und_auftragnehmern");
                        } else {
                            renderStep("angaben_zu_voruebergehender_natur_des_arbeitsausfalls");
                        }
                    });
                }
            );
        }
    },
    {
        "id": "zu_kurze_angaben_zu_produkten_dienstleistungen_auftraggebern_und_auftragnehmern",
        "render": () => {
            textAndInteraction(
                "Deine Angaben zu Produkten, Dienstleistungen und Hauptauftraggebern sind sehr kurz. Du solltest hier genauere Angaben machen.",
                () => createConfirmButton("angaben_zu_produkten_dienstleistungen_auftraggebern_und_auftragnehmern", "Bearbeiten")
            );
        }
    },
    {
        "id": "angaben_zu_voruebergehender_natur_des_arbeitsausfalls",
        "render": () => {
            textAndInteraction(
                "Warum gehst du davon aus, dass dein Unternehmen innerhalb der nächsten 12 Monate wieder voll arbeiten kann?",
                () => {
                    createTextareaInput(state.answers["angaben_zu_voruebergehender_natur_des_arbeitsausfalls"] || null, "Bitte eingeben...", 5).then( (result) => { 
                        setCurrentAnswer(result.value);
                        if(result.value.length < 20) {
                            renderStep("zu_kurze_angaben_zu_voruebergehender_natur_des_arbeitsausfalls");
                        } else {
                            renderStep("sektion_betriebsangaben");
                        }
                    });
                },
                "Um Unterstützung zu erhalten muss der Arbeitsausfall durch die Kurzarbeit vorübergehender Natur sein."
            );
        }
    },
    {
        "id": "zu_kurze_angaben_zu_voruebergehender_natur_des_arbeitsausfalls",
        "render": () => {
            textAndInteraction(
                "Deine Angaben zu der vorübergehenden Natur des Arbeitsausfalls sind sehr kurz. Du solltest hier genaue Angaben machen.",
                () => createConfirmButton("angaben_zu_voruebergehender_natur_des_arbeitsausfalls", "Bearbeiten")
            );
        }
    },
    {
        "id": "sektion_betriebsangaben",
        "render": () => {
            textAndInteraction(
                "Super, dass du es bis hierhin geschafft hast. Als nächstes stelle ich dir einige Fragen rund um dein Unternehmen, damit ich für dich das Formular für die Bundesagentur für Arbeit vorbereiten kann.",
                () => createConfirmButton("betriebsnummer", "Weiter")
            );
        }
    },
    // Hier beginnen die restlichen Fragen for das Formular
    {
        "id": "betriebsnummer",
        "render": () => {
            textAndInteraction(
                "Wie lautet die Betriebsnummer bei der Bundesagentur für Arbeit?",
                () => {
                    createTextInput(state.answers["betriebsnummer"] || "", 1, true, "[0-9]+").then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("betriebsname");
                    });
                },
                "Die Betriebsnummer verwendest du für die Meldung deiner Beschäftigten an die Sozialversicherungsträger. Die Betriebsnummer erhältst du auch von deiner Lohnbuchhaltung."
            );
        }
    },
    {
        "id": "betriebsname",
        "render": () => {
            textAndInteraction(
                "Wie lautet der Name deines Unternehmens?",
                () => {
                    createTextInput(state.answers["betriebsname"] || null, true).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("betriebsadresse");
                    });
                },
                "Hier ist auf die richtige Angabe der Rechtsform zu achten, z.B. Max Mustermann GmbH."
            );
        }
    },
    {
        "id": "betriebsadresse",
        "render": () => {
            textAndInteraction(
                "Wie lautet die Straße und Hausnummer deines Unternehmens?",
                () => {
                    createTextInput(state.answers["betriebsadresse"] || null, true).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("betriebsort");
                    });
                }
            );
        }
    },
    {
        "id": "betriebsort",
        "render": () => {
            textAndInteraction(
                "Wie lautet die PLZ und der Ort deines Unternehmens?",
                () => {
                    createTextInput(state.answers["betriebsort"] || null, true).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("betriebsansprechpartner");
                    });
                }
            );
        }
    },
    {
        "id": "betriebsansprechpartner",
        "render": () => {
            textAndInteraction(
                "Wer ist der Ansprechpartner in deinem Unternehmen bei Fragen zu Kurzarbeit?",
                () => {
                    createTextInput(state.answers["betriebsansprechpartner"] || null, true).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("betriebskontakt");
                    });
                },
            );
        }
    },
    {
        "id": "betriebskontakt",
        "render": () => {
            textAndInteraction(
                "Wie kann dieser Ansprechpartner kontaktiert werden?",
                () => {
                    createTextInput(state.answers["betriebskontakt"] || null, true).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("hat_abweichende_lohnabrechnungsstelle");
                    });
                },
                "Hier kannst du eine Telefonnummer und E-Mail Adresse angeben."
            );
        }
    },
    {
        "id": "hat_abweichende_lohnabrechnungsstelle",
        "render": () => {
            textAndInteraction(
                "Führst du die Lohnunterlagen deiner Mitarbeiter (z.B. Gehaltsabrechnungen, Stundenzettel) in einer anderen Niederlassung deines Unternehmens?",
                () => createYesNoButtons("lohnabrechnungsstellenadresse", "arbeitzeitreduzierung_von")
            );
        }
    },
    {
        "id": "lohnabrechnungsstellenadresse",
        "render": () => {
            textAndInteraction(
                "Wie lautet die Straße und Hausnummer deiner Lohnabrechnungsstelle?",
                () => {
                    createTextInput(state.answers["lohnabrechnungsstellenadresse"] || null, true).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("lohnabrechnungsstellenort");
                    });
                }
            );
        }
    },
    {
        "id": "lohnabrechnungsstellenort",
        "render": () => {
            textAndInteraction(
                "Wie lautet die PLZ und der Ort deiner Lohnabrechnungsstelle?",
                () => {
                    createTextInput(state.answers["lohnabrechnungsstellenort"] || null, true).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("lohnabrechnungsstellenansprechpartner");
                    });
                }
            );
        }
    },    
    {
        "id": "lohnabrechnungsstellenansprechpartner",
        "render": () => {
            textAndInteraction(
                "Wer ist der richtige Ansprechpartner in deinem Unternehmen, wenn es um Lohnabrechnungen geht?",
                () => {
                    createTextInput(state.answers["lohnabrechnungsstellenansprechpartner"] || null, true).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("lohnabrechnungsstellenkontakt");
                    });
                }
            );
        }
    },
    {
        "id": "lohnabrechnungsstellenkontakt",
        "render": () => {
            textAndInteraction(
                "Wie kann dieser Ansprechpartner kontaktiert werden?",
                () => {
                    createTextInput(state.answers["lohnabrechnungsstellenkontakt"] || null, true).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("arbeitzeitreduzierung_von");
                    });
                },
                "Hier kannst du eine Telefonnummer und E-Mail Adresse angeben."
            );
        }
    },
    {
        "id": "arbeitzeitreduzierung_von",
        "render": () => {
            textAndInteraction(
                "Ab welchem Monat planst du die Reduzierung der Arbeitszeit?",
                () => {
                    const date = new Date();              
                    const options = [];
                    for (let i=0; i<6; i++) {
                        const curDateStr = getDateYearAndMonth(createDateShiftedByNumberOfMonths(date, i));

                        options.push({
                            value: curDateStr,
                            text: curDateStr
                        });
                    }

                    createSelect(options, state.answers["arbeitzeitreduzierung_von"] || options[0].value).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("arbeitzeitreduzierung_bis");
                    });
                }
            );
        }
    },
    {
        "id": "arbeitzeitreduzierung_bis",
        "render": () => {
            textAndInteraction(
                "Bis wann planst du die Reduzierung der Arbeitszeit?",
                () => {
                    const date = new Date();
                    const options = [];
                    const fromDateStr = state.answers["arbeitzeitreduzierung_von"];
                    let onOrAfterFromDate = false;
                    let defaultValue = null;
                    for (let i=0; i<18; i++) {
                        const curDateStr = getDateYearAndMonth(createDateShiftedByNumberOfMonths(date, i));
                        if (curDateStr === fromDateStr) {
                            onOrAfterFromDate = true;
                        }

                        const isMinimumOneMonthInFuture = i >= 1;

                        if (isMinimumOneMonthInFuture && onOrAfterFromDate) {
                            options.push({
                                value: curDateStr,
                                text: curDateStr
                            });

                            if (defaultValue === null) {
                                defaultValue = curDateStr;
                            }
                        }
                    }

                    createSelect(options, state.answers["arbeitzeitreduzierung_bis"] || defaultValue).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("besteht_unternehmen_laenger_als_1_jahr");
                    });
                },
                "Bitte möglichst großzügig planen. Ein früheres Ende ist jederzeit möglich, Verlängerungen sind aufwändiger."
            );
        }
    },
    {
        "id": "besteht_unternehmen_laenger_als_1_jahr",
        "render": () => {
            textAndInteraction(
                "Besteht dein Unternehmen länger als ein Jahr?",
                () => createYesNoButtons("gibt_es_einen_tarifvertrag", "betriebsgruendung", "Länger als ein Jahr", "Kürzer als ein Jahr")
            );
        }
    },
    {
        "id": "betriebsgruendung",
        "render": () => {
            textAndInteraction(
                "Seit wann besteht dein Unternehmen?",
                () => {
                    const date = new Date();
                    const options = [];
                    for (let i=-12; i<=0; i++) {
                        const curDateStr = getDateYearAndMonth(createDateShiftedByNumberOfMonths(date, i));
                        options.push({
                            value: curDateStr,
                            text: curDateStr
                        });
                    }

                    createSelect(options, state.answers["betriebsgruendung"] || getDateYearAndMonth(date)).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("gibt_es_einen_tarifvertrag");
                    });
                }
            );
        }
    },

    {
        "id": "gibt_es_einen_tarifvertrag",
        "render": () => {
            textAndInteraction(
                "Gilt für deine Beschäftigten ein Tarifvertrag?",
                () => createYesNoButtons("gibt_es_arbeiter_und_angestellte", "gibt_es_einen_betriebsrat", "Tarifvertrag vorhanden", "Kein Tarifvertrag")
            );
        }
    },    
    {
        "id": "gibt_es_arbeiter_und_angestellte",
        "render": () => {
            textAndInteraction(
                "In meinem Unternehmen gelten Tarifverträge...",
                () => {
                    botui.action.button({
                        action: [
                            {
                                icon: 'wrench',
                                text: '...nur für Arbeiter',
                                value: 'nur_arbeiter'
                            },
                            {
                                icon: 'briefcase',
                                text: '...nur für Angestellte',
                                value: 'nur_angestellte'
                            },
                            {
                                icon: 'building',
                                text: '...für Arbeiter und Angestellte',
                                value: 'beides'
                            }
                        ]
                    }).then( (res) => {
                        setCurrentAnswer(res.value);
                        if (res.value === 'nur_arbeiter') {
                            renderStep("wie_heisst_tarifvertrag_fuer_arbeiter");
                        } else if (res.value === 'nur_angestellte') {
                            renderStep("wie_heisst_tarifvertrag_fuer_angestellte");
                        } else {
                            renderStep("wie_heisst_tarifvertrag_fuer_arbeiter");
                        }
                    });
                },
                "Mehr zur Unterscheidung zwischen Arbeitern und Angestellten findest du [hier](https://www.dbb.de/lexikon/themenartikel/a/angestellte-und-arbeiter.html)^."
            );
        }
    },
    {
        "id": "wie_heisst_tarifvertrag_fuer_arbeiter",
        "render": () => {
            textAndInteraction(
                "Wie heißt der Tarifvertrag für die Arbeiter?",
                () => {
                    createTextInput(state.answers["wie_heisst_tarifvertrag_fuer_arbeiter"] || null, true).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("wieviele_regulaere_wochenstunden_hat_tarifvertrag_fuer_arbeiter");
                    });
                },
                "Beispiel: Entgelt- und Manteltarifvertrag für die Systemgastronomie."
            );
        }
    },
    {
        "id": "wieviele_regulaere_wochenstunden_hat_tarifvertrag_fuer_arbeiter",
        "render": () => {
            textAndInteraction(
                "Wie viele Stunden Wochenarbeitszeit sind für die Arbeiter (Vollzeit) laut Tarifvertrag vereinbart?",
                () => {                    
                    createIntegerInput(state.answers["wieviele_regulaere_wochenstunden_hat_tarifvertrag_fuer_arbeiter"] || 40, 1, null, 0.5).then( (result) => { 
                        setCurrentAnswer(result.value);                           
                        renderStep("tarifvertrag_fuer_arbeiter_enthaelt_kurzarbeitsklausel");
                    });
                }
            );
        }
    },
    {
        "id": "tarifvertrag_fuer_arbeiter_enthaelt_kurzarbeitsklausel",
        "render": () => {
            textAndInteraction(
                "Enthält der Tarifvertrag für die Arbeiter eine Kurzarbeitsklausel?",
                () => {
                    botui.action.button({
                        action: [
                            {
                                icon: 'check',
                                text: "Kurzarbeitsklausel vorhanden",
                                value: true
                            },
                            {
                                icon: 'times',
                                text: "Keine Kurzarbeitsklausel",
                                value: false
                            }
                        ]
                    }).then( (res) => {
                        setCurrentAnswer(res.value);

                        if (state.answers["gibt_es_arbeiter_und_angestellte"] === "nur_arbeiter") {
                            renderStep("hat_tarifvertrag_ankuendigungsfrist");
                        } else {
                            renderStep("wie_heisst_tarifvertrag_fuer_angestellte");
                        }
                    });
                }
            );
        }
    },
    {
        "id": "wie_heisst_tarifvertrag_fuer_angestellte",
        "render": () => {
            textAndInteraction(
                "Wie heißt der Tarifvertrag für Angestellte?",
                () => {
                    createTextInput(state.answers["wie_heisst_tarifvertrag_fuer_angestellte"] || null, true).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("wieviele_regulaere_wochenstunden_hat_tarifvertrag_fuer_angestellte");
                    });
                },
                "Beispiel: Entgelt- und Manteltarifvertrag für die Systemgastronomie."
            );
        }
    },
    {
        "id": "wieviele_regulaere_wochenstunden_hat_tarifvertrag_fuer_angestellte",
        "render": () => {
            textAndInteraction(
                "Wie viele Stunden Wochenarbeitszeit sind für Angestellte (Vollzeit) laut Tarifvertrag vereinbart?",
                () => {                    
                    createIntegerInput(state.answers["wieviele_regulaere_wochenstunden_hat_tarifvertrag_fuer_angestellte"] || 40, 1, null, 0.5).then( (result) => { 
                        setCurrentAnswer(result.value);                           
                        renderStep("tarifvertrag_fuer_angestellte_enthaelt_kurzarbeitsklausel");
                    });
                }
            );
        }
    },
    {
        "id": "tarifvertrag_fuer_angestellte_enthaelt_kurzarbeitsklausel",
        "render": () => {
            textAndInteraction(
                "Enthält der Tarifvertrag für Angestellte eine Kurzarbeitsklausel?",
                () => createYesNoButtons("hat_tarifvertrag_ankuendigungsfrist", "hat_tarifvertrag_ankuendigungsfrist", "Kurzarbeitsklausel vorhanden", "Keine Kurzarbeitsklausel")
            );
        }
    },
    {
        "id": "hat_tarifvertrag_ankuendigungsfrist",
        "render": () => {
            textAndInteraction(
                "Sieht der Tarifvertrag eine Ankündigungsfrist zur Einführung der Kurzarbeit vor?",
                () => createYesNoButtons("beschreibung_ankuendigungsfrist", "gibt_es_einen_betriebsrat", "Ankündigungsfrist vorgesehen", "Keine Ankündigungsfrist")
            );
        }
    },
    {
        "id": "beschreibung_ankuendigungsfrist",
        "render": () => {
            textAndInteraction(
                "Wie lang ist die Ankündigungsfrist?",
                () => {
                    createTextInput(state.answers["beschreibung_ankuendigungsfrist"] || null, true).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("gibt_es_einen_betriebsrat");
                    });
                }
            );
        }
    },
    {
        "id": "gibt_es_einen_betriebsrat",
        "render": () => {
            textAndInteraction(
                "Hat dein Unternehmen einen Betriebsrat?",
                () => createYesNoButtons("wurde_kurzarbeit_vereinbart", "wurde_kurzarbeit_vereinbart", "Betriebsrat existiert", "Kein Betriebsrat")
            );
        }
    },
    {
        "id": "wurde_kurzarbeit_vereinbart",
        "render": () => {
            const esGibtEinenBetriebsrat = (state.answers["gibt_es_einen_betriebsrat"] === true);

            textAndInteraction(
                "Hat dein Unternehmen mit " + (esGibtEinenBetriebsrat ? "dem Betriebsrat" : "den Beschäftigten") + " eine Regelung zur Einführung der Kurzarbeit vereinbart?",
                () => {
                    botui.action.button({
                        action: [
                            {
                                icon: 'check',
                                text: 'Kurzarbeit vereinbart',
                                value: true
                            },
                            {
                                icon: 'times',
                                text: 'Keine Vereinbarung',
                                value: false
                            }
                        ]
                    }).then( (res) => {
                        setCurrentAnswer(res.value);

                        const esGibtEinenBetriebsrat = (state.answers["gibt_es_einen_betriebsrat"] === true);

                        if (res.value === true && esGibtEinenBetriebsrat) {
                            // kurzarbeit vereinbart und es gibt einen betriebsrat
                            state.answers["wie_wurde_vereinbart"] = "Per Betriebsvereinbarung";
                            renderStep("wann_wurde_vereinbart");
                        } else if (res.value === true && !esGibtEinenBetriebsrat) {
                            // kurzarbeit vereinbart und es gibt KEINEN betriebsrat
                            renderStep("wie_wurde_vereinbart");
                        } else if (res.value === false && esGibtEinenBetriebsrat) {
                            // kurzarbeit NICHT vereinbart und es gibt einen betriebsrat
                            // auch hier setzen wir bereits die beantwortung der frage auf "Per Betriebsvereinbarung", da der Hinweis der folgt
                            // einfach nur ein Verstanden abverlangt und dann davon ausgeht, dass diese Vereinbarung nachgeholt wird.
                            state.answers["wie_wurde_vereinbart"] = "Per Betriebsvereinbarung";                            
                            renderStep("hinweis_betriebsvereinbarung");
                        } else {
                            // kurzarbeit NICHT vereinbart und es gibt KEINEN betriebsrat
                            renderStep("hinweis_vereinbarung_oder_aenderungskuendigung");
                        }
                    });
                }
            );
        }
    },
    {
        "id": "hinweis_betriebsvereinbarung",
        "render": () => {
            textAndInteraction(
                "Es ist wichtig, dass du mit eurem Betriebsrat eine Vereinbarung zur Kurzarbeit triffst, bevor dein Unternehmen Kurzarbeit durchführen kann.",
                () => createConfirmButton("wann_wurde_vereinbart", "Verstanden")
            );
        }
    },  
    {
        "id": "hinweis_vereinbarung_oder_aenderungskuendigung",
        "render": () => {
            textAndInteraction(
                "Für die Einführung von Kurzarbeit ist ggf. eine Änderung des Arbeitsvertrags notwendig und muss in gegenseitigem Einvernehmen getroffen werden. Sollte ein Beschäftigter damit nicht einverstanden sein, so besteht die Möglichkeit einer Änderungskündigung (Achtung: Kündigungsfrist beachten!).",
                () => createConfirmButton("wie_wurde_vereinbart", "Verstanden")
            );
        }
    },  
    {
        "id": "wie_wurde_vereinbart",
        "render": () => {
            textAndInteraction(
                "Wie hast du mit deinen Beschäftigten Kurzarbeit vereinbart?",
                () => {
                    const date = new Date();
                    const options = [
                        { value: "Per separater Vereinbarung", text: "Per separater Vereinbarung"},                        
                        { value: "Per Änderungskündigung", text: "Per Änderungskündigung"},
                        { value: "Per existierender Regelung im Arbeitsvertrag", text: "Per existierender Regelung im Arbeitsvertrag"}
                    ];
                    let defaultValue = "Per separater Vereinbarung";

                    createSelect(options, state.answers["wie_wurde_vereinbart"] || defaultValue).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("wann_wurde_vereinbart");
                    });
                }
            );
        }
    },
    {
        "id": "wann_wurde_vereinbart",
        "render": () => {
            textAndInteraction(
                "Wann wurde die Vereinbarung getroffen?",
                () => {
                    createDateInput(state.answers["wann_wurde_vereinbart"] || null).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("wieviele_leiharbeiter_in_gesamtbetrieb_bzw_betriebsabteilung");
                    });
                },
		        "Sollten Vereinbarungen an verschiedenen Tagen abgeschlossen worden sein, so kannst du hier das spätere Datum wählen."
            );
        }
    },
    {
        "id": "wieviele_leiharbeiter_in_gesamtbetrieb_bzw_betriebsabteilung",
        "render": () => {
            textAndInteraction(
                state.answers["arbeitzeitreduzierung_fuer"] === "Abteilung" ? "Wieviele Leiharbeiter beschäftigst du in der betroffenen Abteilung?" : "Wieviele Leiharbeiter beschäftigst du in deinem Unternehmen?",
                () => {
                    const betrifftAbteilung = state.answers["arbeitzeitreduzierung_fuer"] === "Abteilung";

                    createIntegerInput(state.answers["wieviele_leiharbeiter_in_gesamtbetrieb_bzw_betriebsabteilung"] || "0", 0, (betrifftAbteilung ? state.answers["anzahl_beschaeftigte_in_abteilung"] : state.answers["anzahl_beschaeftigte"]),1,false).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("ist_arbeitsausfall_massgeblich_branchenueblich_betriebsueblich_oder_saisonbedingt");
                    });
                },
                "Das Kurzarbeitergeld für Leiharbeiter wird über den Verleiher abgewickelt."
            );
        }
    },  
    {
        "id": "ist_arbeitsausfall_massgeblich_branchenueblich_betriebsueblich_oder_saisonbedingt",
        "render": () => {
            textAndInteraction(
                "Sind für den Arbeitsausfall _auch andere_ Ursachen maßgeblich?",
                () => {
                    botui.action.button({
                        action: [
                            {
                                icon: 'times',
                                text: "Branchenübliche Ursachen",
                                value: true
                            },
                            {
                                icon: 'times',
                                text: "Betriebsübliche Ursachen",
                                value: true
                            },
                            {
                                icon: 'times',
                                text: "Saisonbedingte Ursachen",
                                value: true
                            },
                            {
                                icon: 'check',
                                text: "Keine andere üblichen Ursachen",
                                value: false
                            }
                        ]
                    }).then( (res) => {
                        setCurrentAnswer(res.value);
                        renderStep("pdf_erstellung");
                    });
                },
                "Beispiele: Skiliftschließung im Sommer, Eisdiele im Winter, Änderung von internen Betriebsabläufen"
            );
        }
    },
    {
        "id": "pdf_erstellung",
        "render": () => {
            textAndInteraction(
                "Das war's! Ich habe jetzt alle notwendigen Informationen von dir erhalten und erstelle nun das Anzeigeformular. Bitte habe einen Moment Geduld.",
                () => {
                    preLoadPdf();
                    showLastPageWithPdfDownload();
                }
            );
        }
    },
    {
        "id": "ende_ohne_pdf",
        "render": () => {
            botui.message.add({
                content: 'Vielen Dank, dass du meine Hilfe genutzt hast. Falls du Ideen zur Verbesserung dieses Angebots hast, dann freue ich mich sehr, wenn du mir eine E-Mail an hallo@kurzarbeit-einfach.de schickst.',
                hint_content: "Es gibt andere Möglichkeiten. Schau' doch mal nach unter [taxy.io](https://www.taxy.io/corona-hilfe-fuer-unternehmen)^ oder [wir-bleiben-liqui.de](https://wir-bleiben-liqui.de/)^. <br/> Viele Informationen zum Kurzarbeitergeld findest du auch auf [arbeitsagentur.de](https://www.arbeitsagentur.de)^. Filme zu den erforderlichen Vordrucken der Arbeitsagenturen stehen auch auf [YouTube](https://www.youtube.com/channel/UCAqWLJfbZCzDhBo9i4xGDtQ)^ zur Verfügung.",
                human: false
            });
        }
    }
];

var state = {
    currentStep: 0,
    path: [],
    answers: {}
};

/**
 * Search for first answer with "null"-answer + draw
 */
function renderStep(stepId)
{
    if (stepId === null) {        
        return;
    }

    var matchingSteps = steps.filter((v) => {return v.id === stepId});

    if(matchingSteps.length > 0) {
        state.currentStep = stepId;
        state.path.push(stepId);
        matchingSteps[0].render();

        showBackButton();
    }
};

function hideBackButton()
{
    $('#backButton').hide();
}

function showBackButton()
{
    if (state.path.length > 2) {
        $('#backButton').show();
    } else {
        hideBackButton();
    }
}

function textAndInteraction(msg,interaction,hint="")
{
    createTextMessage(msg,hint).then(interaction);
}

function exitTextAndModifyQuestion(exitReason, backStepId, hint="")
{
    textAndInteraction(
        `${exitReason} Möchtest du deine Antwort nochmals bearbeiten?`,
        () => {
            botui.action.button({
                action: [
                    {
                        icon: 'check',
                        text: 'Bearbeiten',
                        value: true
                    },
                    {
                        icon: 'times',
                        text: 'Ende',
                        value: false
                    }
                ]
            }).then( (res) => {
                setCurrentAnswer(res.value);
                if (res.value) {
                    renderStep(backStepId);
                } else {
                    renderStep("ende_ohne_pdf");
                }
            });
        },
        hint !== "" ? hint : "Es gibt andere Möglichkeiten. Schau' doch mal nach unter [taxy.io](https://www.taxy.io/corona-hilfe-fuer-unternehmen)^ oder [wir-bleiben-liqui.de](https://wir-bleiben-liqui.de/)^."
    );
}

function setCurrentAnswer(value)
{
    state.answers[state.currentStep] = value;
}

function restartConversation(step = "hallo")
{
    state.answers = {};
    state.path = [];
    renderStep(step);
}

function goBack()
{
    // second last element holds the step id of the previous question
    const stepId = state.path[state.path.length-2];

    // remove everything until there from the path
    state.path = state.path.slice(0, -2);

    // now let the conversation continue by showing the user wants to see the previous question
    // followed by the previous step.
    botui.message.add({
        content: "Vorherige Frage nochmal",
        human: true
    }).then((res) => {
        // This is a "trick" to force botui to close it's potentially existing button actions from the
        // current step before moving on to the previous step. Otherwise botui does not re-render the
        // buttons with those from the previous step.

        // TODO: Investigate why botui does not re-render button actions when calling botui.action.button(...) multiple times in a row without user interacting in-between
        botui.action.hide();
        renderStep(stepId);
    });
}

function createTextMessage(msg,hint="",human=false)
{
    return botui.message.add({
        content: msg,
        hint_content: hint,
        human: human
    });
}

function createYesNoButtons(jumpOnYes,jumpOnNo,yesButtonText="Ja",noButtonText="Nein",addAutoText=true)
{
    botui.action.button({
        addMessage: addAutoText,
        action: [
            {
                icon: 'check',
                text: yesButtonText,
                value: true
            },
            {
                icon: 'times',
                text: noButtonText,
                value: false
            }
        ]
    }).then( (res) => {
        setCurrentAnswer(res.value);
        if(res.value)
            renderStep(jumpOnYes);
        else
            renderStep(jumpOnNo);
    });
}

function createConfirmButton(jumpOnConfirm, confirmText = 'OK',addAutoText=true)
{
    botui.action.button({
        addMessage: addAutoText,
        action: [
            {
                icon: 'check',
                text: confirmText,
                value: true
            }
        ]
    }).then( (res) => {
        setCurrentAnswer(res.value);
        renderStep(jumpOnConfirm);
    });
}

function createIntegerInput(value=1,min=null,max=null,step=1,required=true,addAutoText=true)
{
    return botui.action.text({
        addMessage: addAutoText,
        action: {
            sub_type: "number",
            value: value,
            min: min,
            max: max,
            step: step,
            required: required,
            button: {
                icon: 'chevron-right',
                label: 'OK'
            }
        }
    });
}

function createDateInput(defaultValue = null, required = true,addAutoText=true)
{
    if(defaultValue === null)
        defaultValue = new Date().toISOString().slice(0, 10); // Populates with current date "Y-m-d"
        
    return botui.action.text({
        action: {
            addMessage: addAutoText,
            sub_type: "date",
            value: defaultValue,
            required: required,
            button: {
                icon: 'chevron-right',
                label: 'OK'
            }
        }
    });
}

function createTextInput(defaultValue = null, required = true,addAutoText=true,pattern=null)
{        
    return botui.action.text({
        addMessage: addAutoText,
        action: {
            value: defaultValue,
            pattern: pattern,
            required: required,
            button: {
                icon: 'chevron-right',
                label: 'OK'
            }
        }
    });
}

function createTextareaInput(defaultValue = null,placeholder="Hier schreiben ...",rows=3,addAutoText=true)
{
    return botui.action.textarea({
        addMessage: addAutoText,
        action: {
            placeholder: placeholder,
            rows: rows,
            value: defaultValue,
            button: {
                icon: 'chevron-right',
            }
        }
    });
}

/**
 * Options: [{value: "x", text: "y"},...]
 */
function createSelect(options,defaultValue=null,placeholder="Bitte wählen...",searchselect=false,addAutoText=true)
{
    return botui.action.select({
        addMessage: addAutoText,
        action: {
            placeholder : placeholder, 
            value: defaultValue,
            searchselect : searchselect,
            options : options,
            button: {
              icon: 'check',
              label: 'OK'
            }
        }
    });
}

function createDateShiftedByNumberOfMonths(date, monthOffset)
{
    const dateCopy = new Date(date.getTime());
    dateCopy.setDate(1);
    dateCopy.setMonth(dateCopy.getMonth() + monthOffset);
    return dateCopy;
}

function getDateYearAndMonth(date)
{
    return date.toLocaleString('default', { month: 'long' }) + ' ' + date.getFullYear();
}

/**
 * Convert answers to data structure for Bastian PDF
 */
function getPdfDataFromState() {
    /*
        Get new fake state by:
            1) play through chatbot
            2) JSON.stringify(state)
    */

    //TESTING_START
    /*fakeState = {"currentStep":"pdf_erstellung","path":["anzahl_beschaeftigte","anzahl_beschaeftigte_in_kurzarbeit","anzahl_wochenstunden_der_beschaeftigten_regulaer","anzahl_wochenstunden_der_beschaeftigten_in_kurzarbeit","ist_verursacht_durch_unabwendbares_ereignis_oder_wirtschaftlich","massnahmen_zur_vermeidung_sind_umgesetzt_worden","branche","ursachen_fuer_arbeitsausfall","angaben_zu_produkten_dienstleistungen_auftraggebern_und_auftragnehmern","angaben_zu_voruebergehender_natur_des_arbeitsausfalls","sektion_betriebsangaben","ist_betriebsnummer_bekannt","betriebsnummer","betriebsname","betriebsanschrift","betriebsansprechpartner","betriebskontakt","lohnabrechnungsstellenname","lohnabrechnungsstellenanschrift","lohnabrechnungsstellenansprechpartner","lohnabrechnungsstellenkontakt","arbeitzeitreduzierung_von","arbeitzeitreduzierung_bis","arbeitzeitreduzierung_fuer","arbeitzeitreduzierung_fuer_abteilung","besteht_unternehmen_laenger_als_1_jahr","betriebsgruendung","gibt_es_einen_tarifvertrag","gibt_es_einen_betriebsrat","wurde_kurzarbeit_vereinbart","wie_wurde_vereinbart","wann_wurde_vereinbart","wieviele_leiharbeiter_in_gesamtbetrieb_bzw_betriebsabteilung","ist_arbeitsausfall_massgeblich_branchenueblich_betriebsueblich_oder_saisonbedingt","pdf_erstellung"],"answers":{"anzahl_beschaeftigte":1,"anzahl_beschaeftigte_in_kurzarbeit":1,"anzahl_wochenstunden_der_beschaeftigten_regulaer":40,"anzahl_wochenstunden_der_beschaeftigten_in_kurzarbeit":20,"ist_verursacht_durch_unabwendbares_ereignis_oder_wirtschaftlich":true,"massnahmen_zur_vermeidung_sind_umgesetzt_worden":true,"branche":"andere","ursachen_fuer_arbeitsausfall":"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.","angaben_zu_produkten_dienstleistungen_auftraggebern_und_auftragnehmern":"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.","angaben_zu_voruebergehender_natur_des_arbeitsausfalls":"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.","sektion_betriebsangaben":true,"ist_betriebsnummer_bekannt":true,"betriebsnummer":"123456789","betriebsname":"Clair Grube Entsorgungsbetriebe gGmbH","betriebsanschrift":"Rainer-Zufall-Str. 12","betriebsansprechpartner":"Frau Bauklo","betriebskontakt":"0123456789, mail@baukloh.de","lohnabrechnungsstellenname":"Keins.","lohnabrechnungsstellenanschrift":"asdlkjsad 2,. asdasd","lohnabrechnungsstellenansprechpartner":"asdasd","lohnabrechnungsstellenkontakt":"0123456789","arbeitzeitreduzierung_von":"Mai 2020","arbeitzeitreduzierung_bis":"Februar 2021","arbeitzeitreduzierung_fuer":"betriebsabteilung","arbeitzeitreduzierung_fuer_abteilung":"SEK","besteht_unternehmen_laenger_als_1_jahr":false,"betriebsgruendung":"September 2019","gibt_es_einen_tarifvertrag":false,"gibt_es_einen_betriebsrat":false,"wurde_kurzarbeit_vereinbart":true,"wie_wurde_vereinbart":"einfache_vereinbarung","wann_wurde_vereinbart":"2020-03-22","wieviele_leiharbeiter_in_gesamtbetrieb_bzw_betriebsabteilung":"1","ist_arbeitsausfall_massgeblich_branchenueblich_betriebsueblich_oder_saisonbedingt":false}}
    var state = fakeState; // Overwrite state-Variable only for local scope / testing*/
    //TESTING_END

    var a = state.answers;
  
  	var begruendung = "";
  	begruendung += "** Was sind die Ursachen für den Arbeitsausfall?\n"+a.ursachen_fuer_arbeitsausfall+"\n\n";
  	begruendung += "** Welche Produkte / Dienstleistungen werden angeboten?\n"+a.angaben_zu_produkten_dienstleistungen_auftraggebern_und_auftragnehmern+"\n\n";
    begruendung += "** Warum ist der Arbeitsausfall vorübergehender Natur?\n"+a.angaben_zu_voruebergehender_natur_des_arbeitsausfalls+"\n\n";

    var tarifvertragAnkuendigungZeile1 = "";
    var tarifvertragAnkuendigungZeile2 = "";
    if (typeof a.beschreibung_ankuendigungsfrist !== "undefined") {
        var lines = splitStrIntoLines(a.beschreibung_ankuendigungsfrist, 90);
        tarifvertragAnkuendigungZeile1 = lines[0];
        tarifvertragAnkuendigungZeile2 = lines.length > 1 ? lines.slice(1).join(" ") : "";
    }    

    return {
        "agenturFuerArbeitAnschrift" : "", // leave empty
        "stammNrKug" : "", // leave empty
        "abteilungsNr" : "", // leave empty
        "betriebsNr" : a.betriebsnummer,
      
        "betriebsanschrift": {
          "nameAnschrift": a.betriebsname+"\n"+a.betriebsadresse+"\n"+a.betriebsort,
          "zusaetzlicheKontaktinfos": a.betriebskontakt
        },
        "betriebsanschriftAnsprechpartner": {
            "nameAnschrift": a.betriebsansprechpartner,
            "zusaetzlicheKontaktinfos": a.betriebskontakt
        },
        "lohnabrechnungsstelle": {
            "nameAnschrift": a.hat_abweichende_lohnabrechnungsstelle === true ? a.lohnabrechnungsstellenadresse+"\n"+a.lohnabrechnungsstellenort : "",
            "zusaetzlicheKontaktinfos":a.hat_abweichende_lohnabrechnungsstelle === true ? a.lohnabrechnungsstellenkontakt : ""
        },
        "lohnabrechnungsstelleAnsprechpartner": {
            "nameAnschrift": a.hat_abweichende_lohnabrechnungsstelle === true ? a.lohnabrechnungsstellenansprechpartner : "",
            "zusaetzlicheKontaktinfos": a.hat_abweichende_lohnabrechnungsstelle === true ? a.lohnabrechnungsstellenkontakt : ""
        },
      
        "branche" : a.branche_freitext,
        "zeitraumVon" : {
          "monat" : a.arbeitzeitreduzierung_von.split(" ")[0],
          "jahr" : a.arbeitzeitreduzierung_von.split(" ")[1]
        },
        "zeitraumBis" : {
          "monat" : a.arbeitzeitreduzierung_bis.split(" ")[0],
          "jahr" : a.arbeitzeitreduzierung_bis.split(" ")[1]
        },
        "abteilungsBeschraenkung" : (a.arbeitzeitreduzierung_fuer === 'Abteilung' ? a.arbeitzeitreduzierung_fuer_abteilung : null),
        "vollarbeitArbeitszeit" : a.anzahl_wochenstunden_der_beschaeftigten_regulaer,
        "kurzarbeitArbeitszeit" : a.anzahl_wochenstunden_der_beschaeftigten_in_kurzarbeit,
        "unternehmenAelterAlsEinJahr": a.besteht_unternehmen_laenger_als_1_jahr,
        "unternehmenGruendungsdatum": a.besteht_unternehmen_laenger_als_1_jahr === true ? "" : a.betriebsgruendung,
        "arbeiterTarifvertrag": {
            "bezeichnung": typeof a.wie_heisst_tarifvertrag_fuer_arbeiter !== "undefined" ? a.wie_heisst_tarifvertrag_fuer_arbeiter : "",
            "normaleArbeitszeit": typeof a.wieviele_regulaere_wochenstunden_hat_tarifvertrag_fuer_arbeiter !== "undefined" ? a.wieviele_regulaere_wochenstunden_hat_tarifvertrag_fuer_arbeiter : "",
            "hatKurzarbeitKlausel": typeof a.tarifvertrag_fuer_arbeiter_enthaelt_kurzarbeitsklausel !== "undefined" ? (a.tarifvertrag_fuer_arbeiter_enthaelt_kurzarbeitsklausel === true ? "Ja" : "Nein") : ""
        },
        "angestellteTarifvertrag": {
            "bezeichnung": typeof a.wie_heisst_tarifvertrag_fuer_angestellte !== "undefined" ? a.wie_heisst_tarifvertrag_fuer_angestellte : "",
            "normaleArbeitszeit": typeof a.wieviele_regulaere_wochenstunden_hat_tarifvertrag_fuer_angestellte !== "undefined" ? a.wieviele_regulaere_wochenstunden_hat_tarifvertrag_fuer_angestellte : "",
            "hatKurzarbeitKlausel": typeof a.tarifvertrag_fuer_angestellte_enthaelt_kurzarbeitsklausel !== "undefined" ? (a.tarifvertrag_fuer_angestellte_enthaelt_kurzarbeitsklausel === true ? "Ja" : "Nein") : ""
        },
        "tarifvertragHatAnkuendigungsfrist": a.hat_tarifvertrag_ankuendigungsfrist,
        "tarifvertragAnkuendigungZ1" : tarifvertragAnkuendigungZeile1,
        "tarifvertragAnkuendigungZ2" : tarifvertragAnkuendigungZeile2,
        "betriebNichtTarifgebunden": !a.gibt_es_einen_tarifvertrag,
        "betriebsratVorhanden": a.gibt_es_einen_betriebsrat,
        "kurzarbeitVereinbartDurchBetriebsvereinbarung": (a.wie_wurde_vereinbart == 'Per Betriebsvereinbarung'),
        "kurzarbeitVereinbartDurchVereinbarungMitArbeitnehmern": (a.wie_wurde_vereinbart == 'Per existierender Regelung im Arbeitsvertrag' || a.wie_wurde_vereinbart == 'Per separater Vereinbarung'),
        "kurzarbeitVereinbartDurchAenderungskuendigung": (a.wie_wurde_vereinbart == 'Per Änderungskündigung'),
        "kurzarbeitVereinbartDurchAenderungskuendigungVereinbartAm": parseYmdDateStrIntoLocalDateStr(a.wann_wurde_vereinbart),
        "kurzarbeitVereinbartDurchAenderungskuendigungWirksamZu": parseYmdDateStrIntoLocalDateStr(a.wann_wurde_vereinbart),    // Same as vereinbarung / make it less complex
      	"kurzarbeitVereinbartDurchSonstiges": (a.wie_wurde_vereinbart == 'Per existierender Regelung im Arbeitsvertrag'),
        "kurzarbeitVereinbartAnmerkungen": (a.wie_wurde_vereinbart == 'Per existierender Regelung im Arbeitsvertrag' ? "Per existierender Regelung im Arbeitsvertrag" : ""),
        "anzahlArbeitnehmerInBetroffenerAbteilung": a.arbeitzeitreduzierung_fuer === 'Abteilung' ? a.anzahl_beschaeftigte_in_abteilung : a.anzahl_beschaeftigte,
        "anzahlLeiharbeiterInBetroffenerAbteilung": a.wieviele_leiharbeiter_in_gesamtbetrieb_bzw_betriebsabteilung,
        "anzahlArbeitnehmerMitEntgeltAusfall": a.anzahl_beschaeftigte_in_kurzarbeit,
        "angabenArbeitsausfall": "- siehe Anlage -",
        "angabenArbeitsausfallAnlage": begruendung,
        "auchUeblicheUrsachenFuerAusfall": a.ist_arbeitsausfall_massgeblich_branchenueblich_betriebsueblich_oder_saisonbedingt,
        "ortDatum": getCurrentLocalDateStr(),
        "path": state.path
    };
};

/**
 * Splits a given text into lines where each line has a max length specified.
 */
function splitStrIntoLines(text, maxLineLength)
{
    var rows = [];
    var arrOfWords = text.split(" ");
    var curRow = arrOfWords[0];
    var curRowLength = curRow.length;
    for (var i = 1; i < arrOfWords.length; i++) {
        var word = arrOfWords[i];
        curRowLength += word.length + 1;
        if (curRowLength <= maxLineLength) {
            curRow += " " + word;
        } else {
            rows.push(curRow);
            curRow = word;
            curRowLength = word.length;
        }
    }
    rows.push(curRow);
    return rows;
}

function parseYmdDateStrIntoLocalDateStr(str)
{
    return str.substr(8, 2) + "." + str.substr(5, 2) + "." + str.substr(0, 4);
}

function getCurrentLocalDateStr()
{
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = today.getFullYear();

    return dd + "." + mm + "." + yyyy;
}

var pdfCache = null;

/**
 * Lädt die PDF im Hintergrund herunter. Somit haben wir später weniger Wartezeit.
 */
function preLoadPdf(callback)
{
    if(pdfCache === null)
    {
        pdfCache = "LOADING";
        $.ajax({
            method: 'post',
            contentType: 'application/json',
            data: JSON.stringify(getPdfDataFromState()),
            xhrFields: {
                responseType: 'blob'
            },
            url: '/pdf.php',
            success: function(data) {
                pdfCache = data;
            },
            error: function(error) {
                alert('Es ist ein Fehler aufgetreten. Bitte versuchen Sie es erneut.');
            }
        });
    }
}

function downloadPdf()
{
    if(pdfCache === null)
    {
        blockUi();
        preLoadPdf();
        setTimeout(downloadPdf,200);
    }
    else if(pdfCache === "LOADING")
    {
        blockUi();
        setTimeout(downloadPdf,200);
    }
    else
    {
        var a = document.createElement('a');
        var url = window.URL.createObjectURL(pdfCache);
        a.href = url;
        a.download = 'anzeige_arbeitsausfall.pdf';
        document.body.append(a);
        a.click();
        a.remove();
        window.URL.revokeObjectURL(url);
        unblockUi();
    }
}

var botui = new BotUI('my-botui-app');

function runBot()
{
    restartConversation();
}

function blockUi()
{
    if(jQuery('#ui_blocker').length == 0)
        jQuery('body').append('<div id="ui_blocker" style="display: none; background-color: rgba(255,255,255,0.8); position: fixed; z-index: 99999; top: 0; bottom:0; left:0; right:0;"><div style="margin: 0 auto;position: relative;top: 50%;margin-top: -100px;"><svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" style="margin:auto;background:transparent;display:block;" width="200px" height="200px" viewBox="0 0 100 100" preserveAspectRatio="xMidYMid"><circle cx="50" cy="50" r="32" stroke-width="8" stroke="#0e3745" stroke-dasharray="50.26548245743669 50.26548245743669" fill="none" stroke-linecap="round" transform="rotate(289.758 50 50)"><animateTransform attributeName="transform" type="rotate" dur="2.2222222222222223s" repeatCount="indefinite" keyTimes="0;1" values="0 50 50;360 50 50"></animateTransform></circle><circle cx="50" cy="50" r="23" stroke-width="8" stroke="#71c9b8" stroke-dasharray="36.12831551628262 36.12831551628262" stroke-dashoffset="36.12831551628262" fill="none" stroke-linecap="round" transform="rotate(-289.758 50 50)"><animateTransform attributeName="transform" type="rotate" dur="2.2222222222222223s" repeatCount="indefinite" keyTimes="0;1" values="0 50 50;-360 50 50"></animateTransform></circle></svg></div>');
    jQuery('#ui_blocker').fadeIn();
}

function unblockUi()
{
    jQuery('#ui_blocker').fadeOut(function(){
        jQuery('#ui_blocker').remove();
    });
}
