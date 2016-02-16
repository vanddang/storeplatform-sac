package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.BackgroundProducer;
import nl.captcha.backgrounds.TransparentBackgroundProducer;
import nl.captcha.gimpy.GimpyRenderer;
import nl.captcha.gimpy.RippleGimpyRenderer;
import nl.captcha.noise.CurvedLineNoiseProducer;
import nl.captcha.noise.NoiseProducer;
import nl.captcha.text.producer.DefaultTextProducer;
import nl.captcha.text.producer.NumbersAnswerProducer;
import nl.captcha.text.producer.TextProducer;
import nl.captcha.text.renderer.DefaultWordRenderer;
import nl.captcha.text.renderer.WordRenderer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class CaptchaGenerator implements InitializingBean {

    private BackgroundProducer backgroundProducer;
    private NumbersAnswerProducer numbersAnswerProducer;
    private TextProducer textProducer;
    private WordRenderer wordRenderer;
    private NoiseProducer noiseProducer;
    private GimpyRenderer gimpyRenderer;
    boolean isBorder = false;

    public Captcha createCaptcha(int width, int height) {
        Captcha.Builder builder = new Captcha.Builder(width, height)
                .addBackground(backgroundProducer)
                .addText(textProducer, wordRenderer)
                //.addText(numbersAnswerProducer)
                .addNoise(noiseProducer);

        if (isBorder) {
            builder.addBorder();
        }

        return builder.build();
    }

    public void setBackgroundProducer(BackgroundProducer backgroundProducer) {
        this.backgroundProducer = backgroundProducer;
    }

    public void setTextProducer(TextProducer textProducer) {
        this.textProducer = textProducer;
    }

    public void setWordRenderer(WordRenderer wordRenderer) {
        this.wordRenderer = wordRenderer;
    }

    public void setNoiseProducer(NoiseProducer noiseProducer) {
        this.noiseProducer = noiseProducer;
    }

    public void setGimpyRenderer(GimpyRenderer gimpyRenderer) {
        this.gimpyRenderer = gimpyRenderer;
    }

    public void setBorder(boolean isBorder) {
        this.isBorder = isBorder;
    }

    public NumbersAnswerProducer getNumbersAnswerProducer() {
        return numbersAnswerProducer;
    }

    public void setNumbersAnswerProducer(NumbersAnswerProducer numbersAnswerProducer) {
        this.numbersAnswerProducer = numbersAnswerProducer;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.backgroundProducer == null) {
            this.backgroundProducer = new TransparentBackgroundProducer();
        }

        if (this.textProducer == null) {
            this.textProducer = new DefaultTextProducer();
        }

        if (this.wordRenderer == null) {
            this.wordRenderer = new DefaultWordRenderer();
        }

        if (this.numbersAnswerProducer == null) {
            this.numbersAnswerProducer = new NumbersAnswerProducer();
        }

        if (this.noiseProducer == null) {
            this.noiseProducer = new CurvedLineNoiseProducer();
        }

        if (this.gimpyRenderer == null) {
            this.gimpyRenderer = new RippleGimpyRenderer();
        }
    }
}
