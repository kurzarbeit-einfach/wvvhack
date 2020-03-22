var steps = [
    {
        "id": "hallo",
        "render": () => {
            textAndInteraction(
                "Mit den Grundlagen kennst du dich jetzt aus. Lass' uns anfangen das Formular auszufüllen. Dazu stelle ich dir eine Reihe von Fragen. Wenn wir damit durch sind, bekommst du von mir die fertig ausgefüllte PDF zum Einreichen bei der Bundesagentur für Arbeit. Los geht's!",
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
                    createIntegerInput(1, 0, null, 1, 1, false).then( (result) => { 
                        setCurrentAnswer(result.value);

                        if (result.value == 1) {
                            createTextMessage("Ich habe zur Zeit _einen Beschäftigten_.","",true);
                        } else {
                            createTextMessage("Ich habe zur Zeit "+result.value+" Beschäftigte_.","",true);
                        }
                        
                        if (result.value <= 0) {
                            renderStep("keine_beschaeftigte");
                        } else if(result.value <= 50) {
                            renderStep("anzahl_beschaeftigte_in_kurzarbeit");
                        } else {
                            renderStep("anzahl_beschaeftigte_hoch"); 
                        }
                    });
                },
                "Es zählen alle Beschäftigten und Leiharbeiter, außer Auszubildende."
            );
        }
    },
    {
        "id": "keine_beschaeftigte",
        "render": () => {
            exitTextAndModifyQuestion(
                "Für Unternehmen ohne Beschäftigte kann leider kein Geld für Kurzarbeit beantragt werden.",
                "anzahl_beschaeftigte",
                "Es gibt andere Möglichkeiten. Schau' doch mal nach unter [taxy.io](https://www.taxy.io/corona-hilfe-fuer-unternehmen)^ oder [wir-bleiben-liqui.de](https://wir-bleiben-liqui.de/)^."
            );
        }
    },
    {
        "id": "anzahl_beschaeftigte_hoch",
        "render": () => {
            textAndInteraction(
                "Wir haben diese Hilfe nicht für Unternehmen mit mehr als 50 Beschäftigten optimiert. Grundsätzlich empfehlen wir für Unternehmen mit entsprechender Größe Beratung in Anspruch zu nehmen. Möchtest du dennoch fortfahren?",
                () => createYesNoButtons("anzahl_beschaeftigte_in_kurzarbeit", "ende_ohne_pdf", "Weiter", "Ende")
            );
        }
    },
    {
        "id": "anzahl_beschaeftigte_in_kurzarbeit",
        "render": () => {
            textAndInteraction(
                "Wieviele Beschäftigte sollen in Kurzarbeit gehen?",
                () => {                    
                    createIntegerInput(1, 0, state.answers["anzahl_beschaeftigte"], 1, true, false).then( (result) => { 
                        setCurrentAnswer(result.value);

                        if(result.value == 1)
                            createTextMessage(`Es soll _ein Beschäftigter_ in Kurzarbeit gehen.`,"",true);
                        else
                            createTextMessage(`Es sollen _${result.value} Beschäftigte_ in Kurzarbeit gehen.`,"",true);

                        if (result.value <= 0) {
                            renderStep("keine_beschaeftigte_in_kurzarbeit");
                        } else if((result.value / state.answers["anzahl_beschaeftigte"] * 100) < 10) {
                            renderStep("anzahl_beschaeftigte_in_kurzarbeit_kleiner_10_prozent");
                        } else {
                            renderStep("anzahl_wochenstunden_der_beschaeftigten_regulaer");
                        }
                    });
                }
            );
        }
    },
    {
        "id": "keine_beschaeftigte_in_kurzarbeit",
        "render": () => {
            exitTextAndModifyQuestion(
                "Du möchtest niemanden in Kurzarbeit schicken.",
                "anzahl_beschaeftigte_in_kurzarbeit"
            );
        }
    },
    {
        "id": "anzahl_beschaeftigte_in_kurzarbeit_kleiner_10_prozent",
        "render": () => {
            exitTextAndModifyQuestion(
                `Du möchtest nur ${state.answers["anzahl_beschaeftigte_in_kurzarbeit"]} von ${state.answers["anzahl_beschaeftigte"]} Beschäftigten in Kurzarbeit schicken. Um Unterstützung zu erhalten musst du mindestens 10% deiner Beschäftigten in Kurzarbeit schicken.`,
                "anzahl_beschaeftigte_in_kurzarbeit"
            );
        }
    },
    {
        "id": "anzahl_wochenstunden_der_beschaeftigten_regulaer",
        "render": () => {
            textAndInteraction(
                "Wie viele Stunden arbeiten deine Beschäftigten normalerweise pro Woche?",
                () => {                    
                    createIntegerInput(40, 1, null, 0.5, true, false).then( (result) => { 
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
                "Wie viele Stunden pro Woche sollen diese Beschäftigten arbeiten während der Kurzarbeit?",
                () => {                    
                    createIntegerInput(20, 1, state.answers["anzahl_wochenstunden_der_beschaeftigten_regulaer"], 1, true, false).then( (result) => { 
                        setCurrentAnswer(result.value);

                        
                        if(result.value == 1)
                            createTextMessage(`Sie sollen _eine Stunde_ in der Woche arbeiten, wenn ich Kurzarbeit anordne.`,"",true);
                        else
                            createTextMessage(`Sie sollen _${result.value} Stunden_ in der Woche arbeiten, wenn ich Kurzarbeit anordne.`,"",true);

                        if(result.value > (state.answers["anzahl_wochenstunden_der_beschaeftigten_regulaer"]/1.1)) {
                            renderStep("anzahl_wochenstunden_der_beschaeftigten_in_kurzarbeit_kleiner_10_prozent_geringer");
                        } else {
                            renderStep("ist_verursacht_durch_unabwendbares_ereignis_oder_wirtschaftlich");
                        }
                    });
                }
            );
        }
    },
    {
        "id": "anzahl_wochenstunden_der_beschaeftigten_in_kurzarbeit_kleiner_10_prozent_geringer",
        "render": () => {
            exitTextAndModifyQuestion(
                `Du möchtest die Wochenstunden während der Kurzarbeit von regulär ${state.answers["anzahl_wochenstunden_der_beschaeftigten_regulaer"]} auf ${state.answers["anzahl_wochenstunden_der_beschaeftigten_in_kurzarbeit"]} senken. Um Unterstützung zu erhalten musst du die Wochenstunden um mindestens 10% senken.`,
                "anzahl_wochenstunden_der_beschaeftigten_in_kurzarbeit"
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
                                icon: 'check',
                                text: "Unabwendbares Ereignis",
                                value: true
                            },
                            {
                                icon: 'check',
                                text: "Wirtschaftliche Ursache",
                                value: true
                            },
                            {
                                icon: 'times',
                                text: "Andere Ursache",
                                value: false
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
                "<ul><li>Unabwendbares Ereignis ist z.B. angeordnete Schließung wegen Corona.</li><li>Wirtschaftliche Ursache ist z.B. wegen Corona stornierter Großauftrag.</li></ul>"
            );
        }
    },
    {
        "id": "nicht_verursacht_durch_unabwendbares_ereignis_oder_wirtschaftlich",
        "render": () => {
            exitTextAndModifyQuestion(
                `Die Voraussetzung für Kurzarbeit ist, dass der Arbeitsausfall auf einem unabwendbarem Ereignis basiert oder wirtschaftliche Ursachen hat.`,
                "ist_verursacht_durch_unabwendbares_ereignis_oder_wirtschaftlich"
            );
        }
    },
    {
        "id": "sind_alle_massnahmen_zur_vermeidung_umgesetzt_worden",
        "render": () => {
            textAndInteraction(
                "Sind alle internen Maßnahmen zur Vermeidung von Kurzarbeit umgesetzt worden?",
                () => createYesNoButtons("branche", "massnahmen_zur_vermeidung_sind_nicht_umgesetzt_worden", "Maßnahmen umgesetzt", "Nicht umgesetzt"),
                "Beispiele: Resturlaub aufbrauchen, Überstunden abbauen, Versetzungen von Mitarbeitern"
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
        "id": "branche",
        "render": () => {
            textAndInteraction(
                "Welcher Branche gehört dein Unternehmen an?",
                () => {
                    botui.action.button({
                        action: [                            
                            {
                                icon: 'times',
                                text: "Bauhauptgewerbe",
                                value: "bauhauptgewerbe"
                            },
                            {
                                icon: 'times',
                                text: "Dachdeckerhandwerk",
                                value: "dachdeckerhandwerk"
                            },
                            {
                                icon: 'times',
                                text: "Garten-, Landschafts- und Sportplatzbau",
                                value: "garten_landschaft_und_sportplatzbau"
                            },
                            {
                                icon: 'times',
                                text: "Gerüstbau",
                                value: "geruestbau"
                            },
                            {
                                icon: 'check',
                                text: "Andere Branche",
                                value: "andere"
                            },                     
                        ]               
                    }).then( (result) => {
                        setCurrentAnswer(result.value);
                        if (result.value !== "andere") {
                            renderStep("besondere_branche");
                        } else {
                            renderStep("ursachen_fuer_arbeitsausfall");
                        }
                    });
                }
            );
        }
    },
    {
        "id": "besondere_branche",
        "render": () => {
            textAndInteraction(
                "Für deine Branche gilt eine Sonderbehandlung in den Wintermonaten. Wir raten dir dazu weitere Beratung einzuholen. Möchtest du dennoch fortfahren?",
                () => createYesNoButtons("ursachen_fuer_arbeitsausfall", "ende_ohne_pdf", "Weiter", "Ende")
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
                        if(result.value.length < 30) {
                            renderStep("zu_kurze_ursachen_fuer_arbeitsausfall");
                        } else {
                            renderStep("angaben_zu_produkten_dienstleistungen_auftraggebern_und_auftragnehmern");
                        }
                    });
                },
                "Hier musst du deine individuellen Texte hinterlegen. Wir arbeiten daran, dies mit Standardtexten für typische Fälle zu vereinfachen."
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
                        if (result.value.length < 30) {
                            renderStep("zu_kurze_angaben_zu_produkten_dienstleistungen_auftraggebern_und_auftragnehmern");
                        } else {
                            renderStep("angaben_zu_voruebergehender_natur_des_arbeitsausfalls");
                        }
                    });
                },
                "Hier musst du deine individuellen Texte hinterlegen. Wir arbeiten daran, dies mit Standardtexten für typische Fälle zu vereinfachen."
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
                "Wieso ist der Arbeitsausfall vorübergehender Natur?",
                () => {
                    createTextareaInput(state.answers["angaben_zu_voruebergehender_natur_des_arbeitsausfalls"] || null, "Bitte eingeben...", 5).then( (result) => { 
                        setCurrentAnswer(result.value);
                        if(result.value.length < 30) {
                            renderStep("zu_kurze_angaben_zu_voruebergehender_natur_des_arbeitsausfalls");
                        } else {
                            renderStep("sektion_betriebsangaben");
                        }
                    });
                },
                "Hier musst du deine individuellen Texte hinterlegen. Wir arbeiten daran, dies mit Standardtexten für typische Fälle zu vereinfachen."
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
                "Super, dass du es bis hierhin geschafft hast. Als nächstes stellen wir dir einige Fragen rund um dein Unternehmen, damit wir für dich das Formular für die Bundesagentur für Arbeit vorbereiten können.",
                () => createConfirmButton("betriebsnummer", "Weiter"),
                "Ab hier folgen die typischen Formulareingaben. Leider muss das sein!"
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
                    createIntegerInput(1, 1).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("betriebsname");
                    });
                },
                "<ul><li>Hier geht es _nicht_ um die Betriebsnummer der Krankenkasse oder Berufsgenossenschaft.</li><li>Du kannst diese Nummer bei der Bundesagentur für Arbeit telefonisch erfragen.</li></ul>"
            );
        }
    },
    {
        "id": "betriebsname",
        "render": () => {
            textAndInteraction(
                "Wie lautet der Name deines Unternehmens?",
                () => {
                    createTextInput(null, true).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("betriebsadresse");
                    });
                }
            );
        }
    },
    {
        "id": "betriebsadresse",
        "render": () => {
            textAndInteraction(
                "Wie lautet die Straße und Hausnummer deines Unternehmens?",
                () => {
                    createTextInput(null, true).then( (result) => { 
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
                    createTextInput(null, true).then( (result) => { 
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
                "Wer ist der richtige Ansprechpartner in deinem Unternehmen?",
                () => {
                    createTextInput(null, true).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("betriebskontakt");
                    });
                }
            );
        }
    },
    {
        "id": "betriebskontakt",
        "render": () => {
            textAndInteraction(
                "Wie kann der Ansprechpartner für dein Unternehmen kontaktiert werden?",
                () => {
                    createTextInput(null, true).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("hat_abweichende_lohnabrechnungsstelle");
                    });
                },
                "Hier kannst du eine Telefonnummer oder E-Mail Adresse angeben."
            );
        }
    },
    {
        "id": "hat_abweichende_lohnabrechnungsstelle",
        "render": () => {
            textAndInteraction(
                "Hast du eine externe Lohnbuchhaltung?",
                () => createYesNoButtons("lohnabrechnungsstellenname", "arbeitzeitreduzierung_von"),
                "Zum Beispiel dein Steuerberater"
            );
        }
    },
    {
        "id": "lohnabrechnungsstellenname",
        "render": () => {
            textAndInteraction(
                "Welches Unternehmen übernimmt für dein Unternehmen die Lohnbuchhaltung?",
                () => {
                    createTextInput(null, true).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("lohnabrechnungsstellenadresse");
                    });
                }                
            );
        }
    },
    {
        "id": "lohnabrechnungsstellenadresse",
        "render": () => {
            textAndInteraction(
                "Wie lautet die Straße und Hausnummer deiner Lohnbuchhaltung?",
                () => {
                    createTextInput(null, true).then( (result) => { 
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
                "Wie lautet die PLZ und der Ort deiner Lohnbuchhaltung?",
                () => {
                    createTextInput(null, true).then( (result) => { 
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
                "Wer ist der richtige Ansprechpartner für deine Lohnbuchhaltung?",
                () => {
                    createTextInput(null, true).then( (result) => { 
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
                "Wie kann der Ansprechpartner deiner Lohnbuchhaltung kontaktiert werden?",
                () => {
                    createTextInput(null, true).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("arbeitzeitreduzierung_von");
                    });
                },
                "Hier kannst du eine Telefonnummer oder E-Mail Adresse angeben."
            );
        }
    },
    {
        "id": "arbeitzeitreduzierung_von",
        "render": () => {
            textAndInteraction(
                "Ab wann planst du die Reduzierung der Arbeitszeit?",
                () => {
                    const date = new Date();              
                    const options = [];
                    for (let i=-1; i<6; i++) {
                        const curDateStr = getDateYearAndMonth(createDateShiftedByNumberOfMonths(date, i));

                        options.push({
                            value: curDateStr,
                            text: curDateStr
                        });
                    }

                    createSelect(options, getDateYearAndMonth(date)).then( (result) => { 
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
                    for (let i=-1; i<18; i++) {
                        const curDateStr = getDateYearAndMonth(createDateShiftedByNumberOfMonths(date, i));
                        if (curDateStr === fromDateStr) {
                            onOrAfterFromDate = true;
                        }

                        if (onOrAfterFromDate) {
                            options.push({
                                value: curDateStr,
                                text: curDateStr
                            });
                        }
                    }

                    createSelect(options, state.answers["arbeitzeitreduzierung_von"]).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("arbeitzeitreduzierung_fuer");
                    });
                }
            );
        }
    },
    {
        "id": "arbeitzeitreduzierung_fuer",
        "render": () => {
            textAndInteraction(
                "Betrifft die Kurzarbeit das gesamte Unternehmen oder nur eine Abteilung?",
                () => {
                    botui.action.button({
                        action: [
                            {
                                icon: 'building',
                                text: 'Gesamtes Unternehmen',
                                value: 'gesamtbetrieb'
                            },
                            {
                                icon: 'sitemap',
                                text: 'Abteilung',
                                value: 'betriebsabteilung'
                            }
                        ]               
                    }).then( (res) => {
                        setCurrentAnswer(res.value);
                        if (res.value === 'betriebsabteilung') {
                            renderStep("arbeitzeitreduzierung_fuer_abteilung");
                        } else {
                            renderStep("besteht_unternehmen_laenger_als_1_jahr");
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
                    createTextInput(null, true).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("besteht_unternehmen_laenger_als_1_jahr");
                    });
                }
            );
        }
    },
    {
        "id": "besteht_unternehmen_laenger_als_1_jahr",
        "render": () => {
            textAndInteraction(
                "Besteht dein Unternehmen länger als ein Jahr?",
                () => createYesNoButtons("gibt_es_eines_tarifvertrag", "betriebsgruendung", "Länger als ein Jahr", "Kürzer als ein Jahr")
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

                    createSelect(options, getDateYearAndMonth(date)).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("gibt_es_eines_tarifvertrag");
                    });
                }
            );
        }
    },
    // TODO: Sollten wir diese Exit-Frage nicht weiter nach oben ziehen?
    {
        "id": "gibt_es_eines_tarifvertrag",
        "render": () => {
            textAndInteraction(
                "Hat dein Unternehmen einen Tarifvertrag?",
                () => createYesNoButtons("hat_tarifvertrag", "gibt_es_einen_betriebsrat", "Tarifvertrag existiert", "Kein Tarifvertrag")
            );
        }
    },    
    {
        "id": "hat_tarifvertrag",
        "render": () => {
            exitTextAndModifyQuestion(
                "Diese Hilfe wurde nicht für Unternehmen mit einem Tarifvertrag optimiert. Wir empfehlen dir Rat eines Experten einzuholen.",
                "gibt_es_eines_tarifvertrag"
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
            textAndInteraction(
                "Hat dein Unternehmen mit den Beschäftigten eine Regelung zur Kurzarbeit vereinbart?",
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
                        if (res.value) {
                            renderStep("wie_wurde_vereinbart");
                        } else if (state.answers["gibt_es_einen_betriebsrat"] === true) {
                            renderStep("hinweis_betriebsvereinbarung");
                        } else {
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
                "Es ist wichtig, dass du mit eurem Betriebsrat eine Vereinbarung zur Kurzarbeit triffst, bevor dein Unternehmen Kurzarbeit anzeigen kann.",
                () => createConfirmButton("wie_wurde_vereinbart", "Verstanden")
            );
        }
    },  
    {
        "id": "hinweis_vereinbarung_oder_aenderungskuendigung",
        "render": () => {
            textAndInteraction(
                "Es ist wichtig, dass du mit deinen Beschäftigten eine Vereinbarung zur Kurzarbeit triffst. Sollte ein Beschäftigter damit nicht einverstanden sein, so besteht die Möglichkeit einer Änderungskündigung.",
                () => createConfirmButton("wie_wurde_vereinbart", "Verstanden")
            );
        }
    },  
    {
        "id": "wie_wurde_vereinbart",
        "render": () => {
            textAndInteraction(
                "Wie wurde die Vereinbarung getroffen?",
                () => {
                    const date = new Date();
                    const options = [
                        { value: "einfache_vereinbarung", text: "Per einfacher Vereinbarung"},
                        { value: "arbeitsvertrag", text: "Per Arbeitsvertrag"},
                        { value: "aenderungskuendigung", text: "Per Änderungskündigung"}
                    ];
                    let defaultValue = "einfache_vereinbarung";
                    if (state.answers["gibt_es_einen_betriebsrat"] === true) {
                        options.push({ value: "betriebsvereinbarung", text: "Per Betriebsvereinbarung" });
                        defaultValue = "betriebsvereinbarung";
                    }

                    createSelect(options, defaultValue).then( (result) => { 
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
                    createDateInput().then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("wieviele_leiharbeiter_in_gesamtbetrieb_bzw_betriebsabteilung");
                    });
                }
            );
        }
    },
    {
        "id": "wieviele_leiharbeiter_in_gesamtbetrieb_bzw_betriebsabteilung",
        "render": () => {
            textAndInteraction(
                "Wieviele Leiharbeiter beschäftigst du in deinem Unternehmen bzw. in der betroffenen Abteilung?",
                () => {
                    createIntegerInput(0, 0, state.answers["anzahl_beschaeftigte"]).then( (result) => { 
                        setCurrentAnswer(result.value);
                        renderStep("ist_arbeitsausfall_massgeblich_branchenueblich_betriebsueblich_oder_saisonbedingt");
                    });
                }
            );
        }
    },  
    {
        "id": "ist_arbeitsausfall_massgeblich_branchenueblich_betriebsueblich_oder_saisonbedingt",
        "render": () => {
            textAndInteraction(
                "Sind für den Arbeitsausfall auch _andere_ Ursachen maßgeblich?",
                () => {
                    botui.action.button({
                        action: [
                            {
                                icon: 'check',
                                text: "Branchenübliche Ursachen",
                                value: true
                            },
                            {
                                icon: 'check',
                                text: "Betriebsübliche Ursachen",
                                value: true
                            },
                            {
                                icon: 'check',
                                text: "Saisonbedingte Ursachen",
                                value: true
                            },
                            {
                                icon: 'times',
                                text: "Keine andere üblichen Ursachen",
                                value: false
                            }
                        ]               
                    }).then( (res) => {
                        setCurrentAnswer(res.value);
                        renderStep("pdf_erstellung");
                    });
                }
            );
        }
    },
    {
        "id": "pdf_erstellung",
        "render": () => {
            textAndInteraction(
                "Das war's! Wir haben alle notwendigen Informationen von dir erhalten und erstellen nun das Anzeigeformular. Bitte habe einen Moment Geduld.",
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
                content: 'Vielen Dank, dass du unsere Hilfe genutzt hast. Falls du weitere Ideen zur Verbesserung dieses Angebots hast, dann freuen wir uns riesig, wenn du uns unter hallo@kurzarbeit-einfach.de schreibst.',
                hint_content: "Es gibt andere Möglichkeiten. Schau' doch mal nach unter [taxy.io](https://www.taxy.io/corona-hilfe-fuer-unternehmen)^ oder [wir-bleiben-liqui.de](https://wir-bleiben-liqui.de/)^.",
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
function renderStep(stepId) {

    if(stepId === null)
    {        
        return;
    }

    var matchingSteps = steps.filter((v) => {return v.id === stepId});

    if(matchingSteps.length > 0)
    {
        state.currentStep = stepId;
        state.path.push(stepId);
        matchingSteps[0].render();
    }
};

function textAndInteraction(msg,interaction,hint="")
{
    createTextMessage(msg,hint).then(interaction);
}

function exitTextAndModifyQuestion(exitReason, backStepId)
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
        "Es gibt andere Möglichkeiten. Schau' doch mal nach unter [taxy.io](https://www.taxy.io/corona-hilfe-fuer-unternehmen)^ oder [wir-bleiben-liqui.de](https://wir-bleiben-liqui.de/)^."
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

function createTextInput(defaultValue = null, required = true,addAutoText=true)
{        
    return botui.action.text({
        addMessage: addAutoText,
        action: {
            value: defaultValue,
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
    return new Date(dateCopy.setMonth(dateCopy.getMonth() + monthOffset));
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
    /*fakeState = {"currentStep":"pdf_erstellung","path":["anzahl_beschaeftigte","anzahl_beschaeftigte_in_kurzarbeit","anzahl_wochenstunden_der_beschaeftigten_regulaer","anzahl_wochenstunden_der_beschaeftigten_in_kurzarbeit","ist_verursacht_durch_unabwendbares_ereignis_oder_wirtschaftlich","massnahmen_zur_vermeidung_sind_umgesetzt_worden","branche","ursachen_fuer_arbeitsausfall","angaben_zu_produkten_dienstleistungen_auftraggebern_und_auftragnehmern","angaben_zu_voruebergehender_natur_des_arbeitsausfalls","sektion_betriebsangaben","ist_betriebsnummer_bekannt","betriebsnummer","betriebsname","betriebsanschrift","betriebsansprechpartner","betriebskontakt","lohnabrechnungsstellenname","lohnabrechnungsstellenanschrift","lohnabrechnungsstellenansprechpartner","lohnabrechnungsstellenkontakt","arbeitzeitreduzierung_von","arbeitzeitreduzierung_bis","arbeitzeitreduzierung_fuer","arbeitzeitreduzierung_fuer_abteilung","besteht_unternehmen_laenger_als_1_jahr","betriebsgruendung","gibt_es_eines_tarifvertrag","gibt_es_einen_betriebsrat","wurde_kurzarbeit_vereinbart","wie_wurde_vereinbart","wann_wurde_vereinbart","wieviele_leiharbeiter_in_gesamtbetrieb_bzw_betriebsabteilung","ist_arbeitsausfall_massgeblich_branchenueblich_betriebsueblich_oder_saisonbedingt","pdf_erstellung"],"answers":{"anzahl_beschaeftigte":1,"anzahl_beschaeftigte_in_kurzarbeit":1,"anzahl_wochenstunden_der_beschaeftigten_regulaer":40,"anzahl_wochenstunden_der_beschaeftigten_in_kurzarbeit":20,"ist_verursacht_durch_unabwendbares_ereignis_oder_wirtschaftlich":true,"massnahmen_zur_vermeidung_sind_umgesetzt_worden":true,"branche":"andere","ursachen_fuer_arbeitsausfall":"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.","angaben_zu_produkten_dienstleistungen_auftraggebern_und_auftragnehmern":"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.","angaben_zu_voruebergehender_natur_des_arbeitsausfalls":"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.","sektion_betriebsangaben":true,"ist_betriebsnummer_bekannt":true,"betriebsnummer":"123456789","betriebsname":"Clair Grube Entsorgungsbetriebe gGmbH","betriebsanschrift":"Rainer-Zufall-Str. 12","betriebsansprechpartner":"Frau Bauklo","betriebskontakt":"0123456789, mail@baukloh.de","lohnabrechnungsstellenname":"Keins.","lohnabrechnungsstellenanschrift":"asdlkjsad 2,. asdasd","lohnabrechnungsstellenansprechpartner":"asdasd","lohnabrechnungsstellenkontakt":"0123456789","arbeitzeitreduzierung_von":"Mai 2020","arbeitzeitreduzierung_bis":"Februar 2021","arbeitzeitreduzierung_fuer":"betriebsabteilung","arbeitzeitreduzierung_fuer_abteilung":"SEK","besteht_unternehmen_laenger_als_1_jahr":false,"betriebsgruendung":"September 2019","gibt_es_eines_tarifvertrag":false,"gibt_es_einen_betriebsrat":false,"wurde_kurzarbeit_vereinbart":true,"wie_wurde_vereinbart":"einfache_vereinbarung","wann_wurde_vereinbart":"2020-03-22","wieviele_leiharbeiter_in_gesamtbetrieb_bzw_betriebsabteilung":"1","ist_arbeitsausfall_massgeblich_branchenueblich_betriebsueblich_oder_saisonbedingt":false}}
    var state = fakeState; // Overwrite state-Variable only for local scope / testing*/
    //TESTING_END

    var a = state.answers;
  
  	var begruendung = "";
  	begruendung += "** Was sind die Ursachen für den Arbeitsausfall?\n"+a.ursachen_fuer_arbeitsausfall+"\n\n";
  	begruendung += "** Welche Produkte / Dienstleistungen werden angeboten?\n"+a.angaben_zu_produkten_dienstleistungen_auftraggebern_und_auftragnehmern+"\n\n";
  	begruendung += "** Warum ist der Arbeitsausfall vorübergehender Natur?\n"+a.angaben_zu_voruebergehender_natur_des_arbeitsausfalls+"\n\n";

    return {
        "agenturFuerArbeitAnschrift" : "", // leave empty
        "stammNrKug" : "", // leave empty
        "abteilungsNr" : "", // leave empty
        "betriebsNr" : a.betriebsNr,
      
        "betriebsanschrift": {
          "nameAnschrift": a.betriebsname+"\n"+a.betriebsanschrift,
          "zusaetzlicheKontaktinfos": a.betriebskontakt
        },
        "betriebsanschriftAnsprechpartner": {
            "nameAnschrift": a.betriebsansprechpartner,
            "zusaetzlicheKontaktinfos": a.betriebskontakt
        },
        "lohnabrechnungsstelle": {
            "nameAnschrift": a.lohnabrechnungsstellenname+"\n"+a.lohnabrechnungsstellenanschrift,
            "zusaetzlicheKontaktinfos": a.lohnabrechnungsstellenkontakt
        },
        "lohnabrechnungsstelleAnsprechpartner": {
            "nameAnschrift": a.lohnabrechnungsstellenansprechpartner,
            "zusaetzlicheKontaktinfos": a.lohnabrechnungsstellenkontakt
        },
      
        "branche" : a.branche,
        "zeitraumVon" : {
          "monat" : a.arbeitzeitreduzierung_von.split(" ")[0],
          "jahr" : a.arbeitzeitreduzierung_von.split(" ")[1]
        },
        "zeitraumBis" : {
          "monat" : a.arbeitzeitreduzierung_bis.split(" ")[0],
          "jahr" : a.arbeitzeitreduzierung_bis.split(" ")[1]
        },
        "abteilungsBeschraenkung" : (a.arbeitzeitreduzierung_fuer == 'betriebsabteilung' ? a.arbeitzeitreduzierung_fuer_abteilung : null),
        "vollarbeitArbeitszeit" : a.anzahl_wochenstunden_der_beschaeftigten_regulaer,
        "kurzarbeitArbeitszeit" : a.anzahl_wochenstunden_der_beschaeftigten_in_kurzarbeit,
        "unternehmenAelterAlsEinJahr": a.besteht_unternehmen_laenger_als_1_jahr,
        "unternehmenGruendungsdatum": a.betriebsgruendung,
        "arbeiterTarifvertrag": {
            "bezeichnung": "",							//Hardcoded!
            "normaleArbeitszeit": "",					//Hardcoded!
            "hatKurzarbeitKlausel": ""					//Hardcoded!
        },
        "angestellteTarifvertrag": {
            "bezeichnung": "",							//Hardcoded!
            "normaleArbeitszeit": "",					//Hardcoded!
            "hatKurzarbeitKlausel": ""					//Hardcoded!
        },
        "tarifvertragHatAnkuendigungsfrist": false,		//Hardcoded!
        "betriebNichtTarifgebunden": !a.gibt_es_eines_tarifvertrag,
        "betriebsratVorhanden": a.gibt_es_einen_betriebsrat,
        "kurzarbeitVereinbartDurchBetriebsvereinbarung": (a.wie_wurde_vereinbart == 'betriebsvereinbarung'),
        "kurzarbeitVereinbartDurchVereinbarungMitArbeitnehmern": (a.wie_wurde_vereinbart == 'arbeitsvertrag' || a.wie_wurde_vereinbart == 'einfache_vereinbarung'),
        "kurzarbeitVereinbartDurchAenderungskuendigung": (a.wie_wurde_vereinbart == 'aenderungskuendigung'),
        "kurzarbeitVereinbartDurchAenderungskuendigungVereinbartAm": a.wann_wurde_vereinbart,
        "kurzarbeitVereinbartDurchAenderungskuendigungWirksamZu": a.wann_wurde_vereinbart,    // Same as vereinbarung / make it less complex
      	"kurzarbeitVereinbartDurchSonstiges": (a.wie_wurde_vereinbart == 'arbeitsvertrag'),	//Hardcoded!
        "kurzarbeitVereinbartAnmerkungen": (a.wie_wurde_vereinbart == 'arbeitsvertrag' ? "Im Arbeitsvertrag vereinbart." : ""),			//Hardcoded!
        "anzahlArbeitnehmerInBetroffenerAbteilung": a.anzahl_beschaeftigte,
        "anzahlLeiharbeiterInBetroffenerAbteilung": 0,	//Hardcoded!
        "anzahlArbeitnehmerMitEntgeltAusfall": a.anzahl_beschaeftigte_in_kurzarbeit,
        "angabenArbeitsausfall": "- siehe Anlage -",
        "angabenArbeitsausfallAnlage": begruendung,
        "auchUeblicheUrsachenFuerAusfall": false,
        "ortDatum": "16.11.2019"
    };
};


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


