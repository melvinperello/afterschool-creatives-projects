/**
 *
 * Polaris Java Library Examples - Afterschool Creatives "Captivating Creativity"
 *
 * Copyright 2018 Jhon Melvin Perello
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package org.afterschoolcreatives.polaris.examples.java;

import java.io.File;
import org.afterschoolcreatives.polaris.java.io.ApacheFTPClientFactory;
import org.afterschoolcreatives.polaris.java.io.ApacheFTPClientManager;

/**
 *
 * @author Jhon Melvin
 */
public class E4_FTP {

    public static void main(String[] args) {
        ApacheFTPClientFactory ftp = new ApacheFTPClientFactory();
        ftp.setServer("127.0.0.1");
        ftp.setUsername("ftp-cict");
        ftp.setPassword("123456");
        ftp.setPort(21);

        try (ApacheFTPClientManager man = ftp.createClientManager()) {

            man.setTransferListener((totalBytesTransferred, buffereSize, streamSize) -> {
                System.out.println(totalBytesTransferred + " @ " + buffereSize);
            });

            boolean res = man.download("/voice 1.mp4", new File("voice 1.mp4"));
            System.out.println("Transfer Status: " + res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
